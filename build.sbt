organization := "ch.acmesoftware"

name := "orientdb-scala-dsl"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.1")

fork in Test := true

scalacOptions in Compile ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint"
)

scalacOptions ++= Seq(
  "-J-Xms256M",
  "-J-Xmx1G"
)

libraryDependencies ++= Dependencies.db ++ Dependencies.testing



lazy val orientDbScalaDsl = (project in file("."))
