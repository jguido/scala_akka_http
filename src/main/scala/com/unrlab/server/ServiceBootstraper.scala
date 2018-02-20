package com.unrlab.server

import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.unrlab.api.services.{HelloJsonService, HelloService}
import com.unrlab.common.Loggable

trait ServiceBootstraper extends Loggable
  with ApplicationExceptionHandler
  with HelloService
  with HelloJsonService {

  implicit val exceptionHandler: ExceptionHandler

  val route: Route = handleExceptions(exceptionHandler) {
    helloRoute ~ helloJsonRoute
  }
}
