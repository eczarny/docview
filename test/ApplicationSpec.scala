import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.Helpers._
import play.api.test._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {
  "Application" should {
    "respond with a 404 on a request that does not match a route" in new WithApplication {
      route(FakeRequest(GET, "/docs")) must beNone
    }

    "redirect /docs/:project/:tag to /docs/:project/:tag/index.html" in new WithApplication {
      val path = "/docs/docview/latest"

      redirectLocation(route(FakeRequest(GET, path)).get) must beSome.which(_ == s"$path/index.html")
    }
  }
}
