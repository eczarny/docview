package controllers

import com.amazonaws.services.s3.model.S3Object
import play.Play.application
import play.api.Logger
import play.api.libs.iteratee.Enumerator
import play.api.mvc._

object Application extends Controller {
  private val docviewBucketName = application.configuration.getString("s3.bucketName")

  private val s3Client = s3.S3()

  Logger.info(s"Using the [$docviewBucketName] S3 bucket.")

  def index(project: String, tag: String) = Action {
    Redirect(s"/docs/$project/$tag/index.html")
  }

  def docs(project: String, tag: String, path: String) = Action.async { implicit request =>
    import s3.Contexts.s3ExecutionContext

    Logger.debug(s"$project [$tag] - $path requested")

    s3Client.getObject(docviewBucketName, s"docview/$project/$tag/$path").map(s3Object => result(s3Object))
  }

  private def result(s3Object: S3Object): Result = {
    import s3.Contexts.s3StreamingExecutionContext

    val s3ObjectMetadata = s3Object.getObjectMetadata

    val responseHeader = ResponseHeader(
      OK,
      Map(
        CONTENT_LENGTH -> s3ObjectMetadata.getContentLength.toString,
        CONTENT_TYPE -> s3ObjectMetadata.getContentType
      )
    )

    val body = Enumerator.fromStream(s3Object.getObjectContent)

    Result(responseHeader, body)
  }
}
