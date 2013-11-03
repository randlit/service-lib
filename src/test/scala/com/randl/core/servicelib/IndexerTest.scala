package com.randl.core.servicelib

import org.specs2.mutable.{After, Before, BeforeAfter, SpecificationWithJUnit}
import com.randl.core.servicelib.configuration.Configuration
import java.util.UUID
import com.randl.core.servicelib.elasticsearch.{ElasticSearchRestFactory, ESRestClient}
import io.searchbox.indices.{DeleteIndex, IndicesExists, CreateIndex}
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest
import io.searchbox.core.Index
import com.codahale.jerkson.Json
import org.elasticsearch.index.query.{QueryBuilders, QueryBuilder}

/**
 * Created with IntelliJ IDEA.
 * User: cesar
 * Date: 11/2/13
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
case class Item(id: UUID, description: String)

class Scope extends BeforeAfter with Configuration with ESRestClient {
  val typeES: String = "test"
  val indexES: String = "test"

  def after: Any = {
    val exists = new IndicesExists.Builder(indexES).build()
    if (client.execute(exists).isSucceeded)
      client.execute(new DeleteIndex.Builder(indexES).build())
    ElasticSearchRestFactory.destroy()
  }

  def before: Any = {
    ElasticSearchRestFactory.init()
    val exists = new IndicesExists.Builder("test").build()
    if (!client.execute(exists).isSucceeded)
      client.execute(new CreateIndex.Builder("test").build())
  }
}

class IndexerTest extends SpecificationWithJUnit {
  val typeES: String = "test"
  val indexES: String = "test"

  sequential
  "Rest client for the elasticsearch should   do" should {
    "should create an index" in new Scope {

      val item = Item(UUID.randomUUID(), "description")
      val index = new Index.Builder(Json.generate(item)).index(indexES).`type`(typeES).id("1").build();
      client.execute(index)
      Thread.sleep(5000)
      val result = prepareSearch[Item](QueryBuilders.matchAllQuery())

      result.head must_== item
    }
  }


}
