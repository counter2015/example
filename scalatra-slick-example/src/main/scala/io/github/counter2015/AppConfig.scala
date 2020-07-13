package io.github.counter2015

import com.typesafe.config.ConfigFactory

object AppConfig {
  def load: AppConfig = {
    val cfg = ConfigFactory.load
    val port = cfg.getInt("port")
    AppConfig(port)
  }
}

case class AppConfig(port: Int)
