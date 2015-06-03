package s3

import play.api.Play.current
import play.api.libs.concurrent.Akka

import scala.concurrent.ExecutionContext

object Contexts {

  implicit val s3ExecutionContext: ExecutionContext =
    Akka.system.dispatchers.lookup("execution-contexts.s3")

  implicit val s3StreamingExecutionContext: ExecutionContext =
    Akka.system.dispatchers.lookup("execution-contexts.s3-streaming")
}
