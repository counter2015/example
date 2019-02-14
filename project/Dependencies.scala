import sbt._

object Dependencies {
  // Versions
  val logbackVersion                    =             "1.2.3"
  val scalaLoggingVersion               =             "3.9.0"
  val kafkaVersion                      =             "2.0.1"
  val sparkStreamingKafkaVersion        =             "1.6.3"
  val sparkVersion                      =             "2.4.0"
  val typesafeConfigVersion             =             "1.3.2"
  val jedisVersion                      =             "3.0.0"

  // Libraries
  val logback             = "ch.qos.logback"             %  "logback-classic"       % logbackVersion
  val scalaLogging        = "com.typesafe.scala-logging" %% "scala-logging"         % scalaLoggingVersion
  val kafkaClient         = "org.apache.kafka"           %  "kafka-clients"         % kafkaVersion
  val sparkStreamingKafka = "org.apache.spark"           %% "spark-streaming-kafka" % sparkStreamingKafkaVersion
  val spark               = "org.apache.spark"           %% "spark-streaming"       % sparkVersion                % "provided"
  val typesafeConfig      = "com.typesafe"               %  "config"                % typesafeConfigVersion
  val jedis               = "redis.clients"              %  "jedis"                 % jedisVersion
}
