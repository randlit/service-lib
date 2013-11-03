package com.randl.core.servicelib

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.SpecificationWithJUnit
import com.randl.core.servicelib.configuration.Configuration

/**
 * Created with IntelliJ IDEA.
 * User: cesar
 * Date: 9/18/13
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class ConfigurationTest extends SpecificationWithJUnit with Configuration {

  sequential
  "Configuration should   do" should {
    "should iniziate an entry" in {
      val x = system.getConfig("elasticsearch").getString("hosts")
      x must_== "localhost:9300"
    }
    "rest end point shuold be " in {
      val x = restEndPoint
      x must_== "http://oak-165896.eu-west-1.bonsai.io/articles/"
    }
  }

}
