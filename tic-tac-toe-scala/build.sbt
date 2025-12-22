name := "tic-tac-toe-scala"
version := "1.0"
scalaVersion := "2.12.18"

val jettyVersion = "9.4.50.v20221201"

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.8.2",
  "org.scalatra" %% "scalatra-json" % "2.8.2",
  "org.json4s" %% "json4s-jackson" % "4.0.6",
  "org.eclipse.jetty" % "jetty-server" % jettyVersion,
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
  "javax.servlet" % "javax.servlet-api" % "4.0.1" % "provided",
  "org.scalatest" %% "scalatest" % "3.2.15" % "test"
)

assembly / mainClass := Some("TicTacToeServer")

import sbtassembly.MergeStrategy

assembly / assemblyMergeStrategy := {
  case PathList("module-info.class") => MergeStrategy.discard
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}