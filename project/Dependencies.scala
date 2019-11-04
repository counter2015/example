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
  val hbaseVersion                      =             "2.1.3"
  val hadoopVersion                     =             "2.7.1"
  val prometheusVersion                 =             "0.6.0"

  // Libraries
  val logback              = "ch.qos.logback"             %  "logback-classic"         % logbackVersion
  val scalaLogging         = "com.typesafe.scala-logging" %% "scala-logging"           % scalaLoggingVersion
  val kafkaClient          = "org.apache.kafka"           %  "kafka-clients"           % kafkaVersion
  val sparkStreamingKafka  = "org.apache.spark"           %% "spark-streaming-kafka"   % sparkStreamingKafkaVersion
  val spark                = "org.apache.spark"           %% "spark-streaming"         % sparkVersion                % "provided"
  val typesafeConfig       = "com.typesafe"               %  "config"                  % typesafeConfigVersion
  val jedis                = "redis.clients"              %  "jedis"                   % jedisVersion
  val hadoopCommon         = "org.apache.hadoop"          %  "hadoop-common"           % hadoopVersion
  val hadoopHDFS = "org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion
  val hbaseCommon          = "org.apache.hbase"           %  "hbase-common"            % hbaseVersion
  val hbaseClient          = "org.apache.hbase"           %  "hbase-client"            % hbaseVersion
  val prometheusClient     = "io.prometheus"              %  "simpleclient"            % prometheusVersion
  val prometheusHttpServer = "io.prometheus"              %  "simpleclient_httpserver" % prometheusVersion
  val prometheusServlet    = "io.prometheus"              %  "simpleclient_servlet"    % prometheusVersion
}
