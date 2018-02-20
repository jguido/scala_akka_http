package com.unrlab.server

import akka.http.scaladsl.server.ExceptionHandler

trait ApplicationExceptionHandler {

  import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
  import akka.http.scaladsl.server.Directives._

  val exceptionHandler = ExceptionHandler {
    case e: Throwable =>
      extractUri { uri =>
        println(s"Request to $uri could not be handled normally")
        complete(HttpResponse(StatusCodes.InternalServerError, entity = s"${e.getMessage}"))
      }
  }
}