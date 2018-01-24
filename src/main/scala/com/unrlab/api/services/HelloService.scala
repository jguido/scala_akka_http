package com.unrlab.api.services

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.unrlab.common.Loggable

trait HelloService extends BaseService with Loggable {

  val helloRoute: Route = cors(settings) {
    path("hello") {
      get {
        parameters('name) { name =>
          complete(
            HttpResponse(
              StatusCodes.OK,
              entity = HttpEntity(
                ContentTypes.`text/html(UTF-8)`,
                s"<h1>${name.head.toUpper + name.tail}, say hello to akka-http</h1>"
              )
            )
          )
        }
      }
    }

  }
}
