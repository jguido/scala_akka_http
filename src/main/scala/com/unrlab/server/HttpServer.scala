package com.unrlab.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.unrlab.common.{AppConfig, Loggable}

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

class HttpServer(val config: AppConfig)(implicit val system: ActorSystem, implicit val materializer: ActorMaterializer)
  extends Loggable
    with ServiceBootstraper {


  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  private val host = config.httpConfig.host
  private val port = config.httpConfig.port

  def start(): Unit = {
    Http().bindAndHandle(route, host, port).onComplete {
      case Success(result) => println(s"Server online at http://$host:$port/")
      case Failure(error) => logError(s"Failed to bind to http://$host:$port cause : ${error.getCause.getMessage}")
    }
  }
}
