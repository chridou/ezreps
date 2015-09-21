name := "root"

val commonSettings = Seq(
  organization := "org.chridou",
  version := "0.4",
  scalaVersion := "2.11.6",
  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"))

lazy val core = project.in(file("core"))
  .settings(commonSettings: _*)

lazy val json4s = project.in(file("ezreps-json4s"))
  .settings(commonSettings: _*)
  .dependsOn(core)

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .aggregate(core, json4s)
