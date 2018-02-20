package services

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.unrlab.api.services.HelloService
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class HelloServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with HelloService with BeforeAndAfterAll {
  "HelloService" should {
    "Return Hello John" in {
      val attendedResponse = """<h1>John, say hello to akka-http</h1>"""

      Get("/hello?name=John") ~> helloRoute ~> check {
        responseAs[String] shouldEqual attendedResponse
        status shouldEqual StatusCodes.OK
      }
    }

    "Return greeting with no name given" in {

      Get("/hello?name=") ~> helloRoute ~> check {
        status shouldEqual StatusCodes.OK
      }
    }
  }
}
