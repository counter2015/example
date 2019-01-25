import sbt._

object Dependencies {
  // Versions
  val logbackVersion      = "1.2.3"
  val scalaLoggingVersion = "3.9.0"
  val kafkaVersion = "2.0.1"

  // Libraries
  val logback             = "ch.qos.logback"             % "logback-classic"          % logbackVersion
  val scalaLogging        = "com.typesafe.scala-logging" %% "scala-logging"          % scalaLoggingVersion
  val kafkaClient         = "org.apache.kafka"           % "kafka-clients"            % kafkaVersion
}
