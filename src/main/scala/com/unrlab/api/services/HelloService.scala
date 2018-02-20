package com.unrlab.api.services

import akka.actor.{Actor, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.unrlab.common.Loggable
import akka.util.Timeout
import scala.concurrent.duration._

trait HelloService extends BaseService with Loggable {

  object RequestHandler {
    case class HelloRequest(name: String)
    case class HelloAnswer(answer: String)
  }

  class RequestHandlerActor extends Actor {
    import RequestHandler._

    override def receive: Receive = {
      case HelloRequest(name) =>
        if (name.isEmpty) {
          sender ! HelloAnswer(s"<h1>Anonymous, say hello to akka-http</h1>")
        } else {
          sender ! HelloAnswer(s"<h1>${name.head.toUpper + name.tail}, say hello to akka-http</h1>")
        }
        context.stop(self)
    }

  }
  import akka.pattern.ask

  val helloRoute: Route = cors(settings) {
    path("hello") {
      get {
        implicit val askTimeout: Timeout = 3.seconds
        parameters('name) { name =>
          val actor = system.actorOf(Props(new RequestHandlerActor))
          onSuccess((actor ? RequestHandler.HelloRequest(name)).mapTo[RequestHandler.HelloAnswer]) { result =>
            complete(HttpResponse(
              StatusCodes.OK,
              entity = HttpEntity(
                ContentTypes.`text/html(UTF-8)`,
                result.answer
              )
            ))
          }
        }
      }
    }

  }
}
