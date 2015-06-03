package s3

import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.{AmazonS3, AmazonS3Client}

import scala.concurrent.{ExecutionContext, Future, blocking}

object S3 {

  def apply(): S3 = new S3 {

    protected val s3 = new AmazonS3Client()
  }
}

trait S3 {

  protected def s3: AmazonS3

  def getObject(bucketName: String, key: String)(implicit context: ExecutionContext): Future[S3Object] = Future {
    blocking {
      s3.getObject(bucketName, key)
    }
  }
}
