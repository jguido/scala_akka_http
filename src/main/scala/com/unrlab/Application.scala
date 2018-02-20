package com.unrlab

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.unrlab.common.AppConfig
import com.unrlab.server.HttpServer

object Application extends App {

  val appConfig = ConfigFactory.load("application")
  lazy val config = new AppConfig(appConfig)

  implicit val system: ActorSystem = ActorSystem(s"${config.applicationName}-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val server = new HttpServer(config)
  server.start()
}