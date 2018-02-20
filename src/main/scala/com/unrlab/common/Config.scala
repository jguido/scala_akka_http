package com.unrlab.common

import com.typesafe.config.Config

class AppConfig(load: Config) extends BaseConfig{
  private val config: Config = load.getConfig("unrlab")

  override val httpConfig: HttpConfig = new DefaultHttpConfig(config)
  override val applicationName: String = config.getString("applicationName")
}

trait BaseConfig {
  val applicationName: String
  val httpConfig: HttpConfig
}

trait HttpConfig {
  val port: Int
  val host: String
}

class DefaultHttpConfig(val conf: Config) extends HttpConfig {
  val httpConfig: Config = conf.getConfig("http")
  override val port: Int = httpConfig.getInt("port")
  override val host: String = httpConfig.getString("host")
}
