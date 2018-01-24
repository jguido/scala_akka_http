package com.unrlab.api.services

import akka.http.scaladsl.model
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.unrlab.api.format.JsonSupport
import com.unrlab.api.model.Hello
import com.unrlab.common.Loggable

trait HelloJsonService extends BaseService with Loggable with JsonSupport {

  val helloJsonRoute: Route = cors(settings) {
    path("json-hello") {
      get {
        parameters('name) { name =>
          complete(
            HttpResponse(StatusCodes.OK, entity = HttpEntity(
              model.ContentTypes.`application/json`, helloFormat.write(Hello(name)).toString()))
          )
        }
      }
    }
  }
}
