import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

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

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, false)
  .setPreference(CompactStringConcatenation, false)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(IndentSpaces, 2)
  .setPreference(PreserveSpaceBeforeArguments, false)
  .setPreference(SpaceBeforeColon, false)
  .setPreference(SpaceInsideBrackets, false)
  .setPreference(SpaceInsideParentheses, false)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)

lazy val orientDbScalaDsl = (project in file("."))
