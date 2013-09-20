package com.randl.core.servicelib.elasticsearch

import javax.servlet.{ServletContextEvent, ServletContextListener}
import org.elasticsearch.client.Client
import org.elasticsearch.node.{NodeBuilder, Node}
import org.elasticsearch.common.settings.ImmutableSettings
import com.randl.core.servicelib.configuration.Configuration
import org.elasticsearch.index.query.{QueryBuilder, QueryBuilders}

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 7/22/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Trait to access the elasticsearch client.
 */

/**
 * Holds the elasticsearch client.
 */
object ElasticSearchFactory extends Configuration {
  var client: Client = _
  private var node: Node = _
  lazy val settingDev = ImmutableSettings.settingsBuilder()
  lazy val host: String = system.getConfig("elasticsearch").getString("hosts")
  lazy val clusterName: String = system.getConfig("elasticsearch").getString("clusterName")
  settingDev.put("node.name", "Lasher")
  settingDev.put("discovery.zen.ping.multicast.enabled", false)
  settingDev.put("discovery.zen.ping.unicast.hosts", host)

  def init() {
    node = NodeBuilder.nodeBuilder().client(true).clusterName(clusterName).settings(settingDev).node()
    client = node.client()
  }

  def close() {
    client.close()
    node.close()
  }

}

trait ESClient {

  val indexES: String

  val typeES: String

  def client: Client = ElasticSearchFactory.client

  def prepareSearch = client.prepareSearch(indexES)

  def prepareSearch(cb: QueryBuilder) = client.prepareSearch().setIndices(indexES).setQuery(cb)

}

class ElasticSearchConnectionListener extends ServletContextListener {
  def contextInitialized(p1: ServletContextEvent) {
    ElasticSearchFactory.init()
  }

  def contextDestroyed(p1: ServletContextEvent) {
    ElasticSearchFactory.close()
  }
}

