package com.randl.core.servicelib

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.SpecificationWithJUnit
import com.randl.core.servicelib.configuration.Configuration
import org.specs2.execute.Result

/**
 * Created with IntelliJ IDEA.
 * User: cesar
 * Date: 9/18/13
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */
class ConfigurationScope extends Configuration

@RunWith(classOf[JUnitRunner])
class ConfigurationTest extends SpecificationWithJUnit with Configuration {

  sequential
  "Configuration should do" should {
    "should iniziate an entry" in {
      val x = system.getConfig("elasticsearch").getString("hosts")
      x must_== "127.0.0.1:9300"
    }
  }

}
