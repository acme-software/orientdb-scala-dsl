import sbt._

object Dependencies {

  object Version {
    val scala = "2.11.8"
    val orientDb = "2.2.3"
    val scalatest = "3.0.1"
  }

  val db = Seq(
    "com.orientechnologies" % "orientdb-graphdb" % Version.orientDb
  )

  val testing = Seq(
    "org.scalactic" %% "scalactic" % Version.scalatest,
    "org.scalatest" %% "scalatest" % Version.scalatest % Test,
    "org.mockito" % "mockito-all" % "1.10.19" % Test
  )
}

