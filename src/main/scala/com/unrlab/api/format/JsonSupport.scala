package com.unrlab.api.format

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.unrlab.api.model.Hello
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val helloFormat: RootJsonFormat[Hello] = jsonFormat1(Hello)
}
