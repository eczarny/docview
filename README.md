# docview

[![Build Status](https://travis-ci.org/eczarny/docview.svg)](https://travis-ci.org/eczarny/docview)

Serve private documentation from [Amazon S3][1].

## docview is a work in progress

Though this project promises to serve _private_ documentation there are currently no mechanisms in place to do so. Eventually docview will rely on [GitHub API](https://developer.github.com/v3/) integration to enforce privacy.

## Prerequisites

  - [Amazon S3][1] and the [AWS Command Line Interface][2]
  - [sbt][3]
  - [Docker][4] (on OS X use [boot2docker][5])

## Setting up an S3 bucket for docview

docview needs access to an S3 bucket to serve documentation from. To create the S3 bucket follow Amazon's documentation to [create a bucket](http://docs.aws.amazon.com/AmazonS3/latest/gsg/CreatingABucket.html). Name the new S3 bucket `docview`.

For security it is best to only give docview read-only access to the new `docview` S3 bucket. docview only needs to be able to use the `ListBucket` and `GetObject` S3 actions.

The user that created the S3 bucket should have write access.

## Publishing documentation to an S3 bucket

Use the [AWS Command Line Interface][2] to publish documentation to the `docview` S3 bucket. Assuming `aws` is currently using credentials with write access to the `docview` S3 bucket publishing documentation is easy:

    $ aws s3 sync PROJECT/docs/ s3://docview/PROJECT/latest

The [sync](http://docs.aws.amazon.com/cli/latest/reference/s3/sync.html) command will sync the local `PROJECT/docs/` path to the `s3://docview/PROJECT/latest` S3 path; `PROJECT` is the name of the project the documentation is for. By convention docview uses the `latest` tag for the most recent version of the documentation.

## Development

To run docview locally with auto-reloading enabled:

    $ sbt run

docview should be running on port 9000. To view the latest documentation for a project open http://127.0.0.1:9000/docs/PROJECT/latest in the browser. Replace `PROJECT` with the name of the project used to publish documentation in the section above.

To run tests:

    $ sbt test

A Makefile has also been provided. `make build` builds the docview Docker image, `make run` runs a docview Docker container locally. `make help` will show all available Make targets.

## Deployment

First build the docview Docker image:

    $ make build

Once built the Docker image can be used for deployment.

[1]: http://aws.amazon.com/s3/
[2]: http://aws.amazon.com/cli/
[3]: http://www.scala-sbt.org
[4]: https://www.docker.com/
[5]: http://boot2docker.io/
