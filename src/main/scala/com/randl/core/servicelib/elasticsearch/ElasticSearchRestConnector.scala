package com.randl.core.servicelib.elasticsearch

import javax.servlet.{ServletContextListener, ServletContextEvent}
import io.searchbox.client.{JestClientFactory, JestClient}
import io.searchbox.client.config.ClientConfig
import com.randl.core.servicelib.configuration.Configuration
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.index.query.QueryBuilder
import io.searchbox.core.Search
import scala.collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: cesar
 * Date: 11/2/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */

object ElasticSearchRestFactory extends Configuration {
  var client: JestClient = _
  private var node: Option[JestClient] = None

  def init() {
    if (node.isEmpty) {
      val clientConfig = new ClientConfig.Builder(restEndPoint).multiThreaded(true).build()
      val factory = new JestClientFactory()
      factory.setClientConfig(clientConfig)
      node = Some(factory.getObject)
    }
    client = node.get
  }

  def destroy() {
    client.shutdownClient()
    node = None
  }
}

trait ESRestClient {

  val indexES: String

  val typeES: String

  def client: JestClient = ElasticSearchRestFactory.client


  def prepareSearch[T: Manifest](cb: QueryBuilder): List[T] = {
    val searchSourceBuilder = new SearchSourceBuilder()
    searchSourceBuilder.query(cb)
    val search = new Search.Builder(searchSourceBuilder.toString())
      .addIndex(indexES)
      .addIndex(indexES)
      .build();
    val result = client.execute(search).getSourceAsObjectList(manifest.erasure.asInstanceOf[Class[T]])
    result.toList
  }

}

class ElasticSearchRestConnectionListener extends ServletContextListener {
  def contextInitialized(p1: ServletContextEvent) {
    ElasticSearchFactory.init()
  }

  def contextDestroyed(p1: ServletContextEvent) {
    ElasticSearchFactory.close()
  }
}