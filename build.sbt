name := "root"

val commonSettings = Seq(
  organization := "org.chridou",
  version := "0.1",
  scalaVersion := "2.11.6",
  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"))

lazy val core = project.in(file("core"))
  .settings(commonSettings: _*)

lazy val json4s = project.in(file("ezreps-json4s"))
  .settings(commonSettings: _*)

lazy val root = project.in(file("."))
  .aggregate(core, json4s)
