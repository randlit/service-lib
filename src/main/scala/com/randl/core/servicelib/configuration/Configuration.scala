package com.randl.core.servicelib.configuration

/**
 * Created with IntelliJ IDEA.
 * User: cesar
 * Date: 9/18/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

import com.typesafe.config.ConfigFactory

object Configuration {
  private val config = ConfigFactory.load

}

trait Configuration {
  // namespace of the apcConfig => override in your class if needed => defaults to package name of the class
  /**
   * load apc config
   */
  def config = Configuration.config

  def system = config.getConfig("system")

}