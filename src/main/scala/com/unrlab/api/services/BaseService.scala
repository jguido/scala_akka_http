package com.unrlab.api.services

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.HttpOriginRange
import akka.http.scaladsl.server.Directives
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.unrlab.common.Loggable

trait BaseService extends Directives with Loggable{
  implicit val system: ActorSystem

  val settings: CorsSettings.Default = CorsSettings.defaultSettings.copy(allowedOrigins = HttpOriginRange.*, allowCredentials = false)
}
