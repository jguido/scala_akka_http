package services

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.unrlab.api.services.HelloJsonService
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class HelloJsonServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with HelloJsonService with BeforeAndAfterAll {
  "HelloJsonService" should {
    "Return Hello John in JSON" in {
      val attendedResponse = """{"name":"John"}"""

      Get("/json-hello?name=John") ~> helloJsonRoute ~> check {
        responseAs[String] shouldEqual attendedResponse
        status shouldEqual StatusCodes.OK
      }
    }
  }
}
