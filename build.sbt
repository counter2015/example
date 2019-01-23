import Dependencies._

ThisBuild / scalaVersion     := "2.11.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.github.counter2015"

lazy val global = (project in file("."))
  .settings(
  )
  .aggregate(
    logging,
    kafka,
  )

lazy val logging = (project in file("scala-logging"))
  .settings(
    name := "LoggingExample",
    assemblySettings,
    assemblyJarName in assembly := "scala-logging-example.jar",
    mainClass in assembly := Some("com.github.counter2015.Main"),
    libraryDependencies ++= Seq(
      logback,
      scalaLogging
    )
  )

lazy val kafka = (project in file("kafka"))
  .settings(
    name := "KafkaExample",
    libraryDependencies ++= Seq(
        //scalaLogging
    )
  )

lazy val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case PathList("org", "apache", "spark", xs@_*) => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
  },
)
