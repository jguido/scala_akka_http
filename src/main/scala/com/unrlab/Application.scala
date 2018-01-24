package com.unrlab

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.ExceptionHandler
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.unrlab.api.services.{HelloJsonService, HelloService}
import com.unrlab.common.{AppConfig, Loggable}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Application extends App with HelloService with HelloJsonService with Loggable with ApplicationExceptionHandler {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val appConfig = ConfigFactory.load("application")

  lazy val config = new AppConfig(appConfig)

  val route = handleExceptions(exceptionHandler) {
    helloRoute ~ helloJsonRoute
  }

  private val host = config.httpConfig.host
  private val port = config.httpConfig.port
  val bindingFuture = Http().bindAndHandle(route, host, port)

  bindingFuture.failed.foreach { ex =>
    logError(s"Failed to bind to http://$host:$port cause : ${ex.getMessage}")
  }

  println(s"Server online at http://$host:$port/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}

trait ApplicationExceptionHandler {
  import akka.http.scaladsl.server.Directives._
  import akka.http.scaladsl.model.{HttpResponse, StatusCodes}

  val exceptionHandler = ExceptionHandler {
    case e: Throwable =>
      extractUri { uri =>
        println(s"Request to $uri could not be handled normally")
        complete(HttpResponse(StatusCodes.InternalServerError, entity = s"${e.getMessage}"))
      }
  }
}
