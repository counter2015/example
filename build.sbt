import Dependencies._

ThisBuild / scalaVersion := "2.11.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.counter2015"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature" // Emit warning and location for usages of features that should be imported explicitly.
)

lazy val global = (project in file("."))
  .settings(
  )
  .aggregate(
    logging,
    kafka,
    kafka_streaming_redis,
    hbase,
    prometheusExporters,
    hdfs
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
    assemblySettings,
    assemblyJarName in assembly := "kafka-example.jar",
    libraryDependencies ++= Seq(
      kafkaClient
    )
  )

lazy val kafka_streaming_redis = (project in file("kafka-streaming-redis"))
  .settings(
    name := "KafkaSparkStreamingRedisExample",
    assemblySettings,
    assemblyJarName in assembly := "kafka-spark-redis-example.jar",
    libraryDependencies ++= Seq(
      spark,
      sparkStreamingKafka,
      scalaLogging,
      typesafeConfig,
      jedis
    )
  )

lazy val hbase = (project in file("hbase"))
  .settings(
    name := "HBaseExample",
    libraryDependencies ++= Seq(
      hadoopCommon,
      hbaseCommon,
      hbaseClient
    )
  )

lazy val prometheusExporters = (project in file("prometheus-exporters"))
  .settings(
    name := "CustomPrometheusExporters",
    assemblySettings,
    assemblyJarName in assembly := "my-prometheus-exporters.jar",
    libraryDependencies ++= Seq(
      prometheusClient,
      prometheusHttpServer,
      prometheusServlet,
      typesafeConfig
    )
  )

lazy val hdfs = (project in file("hdfs"))
  .settings(
    name := "SimpleHDFSClient",
    assemblySettingsHDFS,
    assemblyJarName in assembly := "simple-hdfs-client.jar",
    libraryDependencies ++= Seq(
      hadoopCommon,
      hadoopHDFS,
      typesafeConfig
    )
  )

lazy val scalatraDemo = (project in file("scalatra-slick-example"))
  .enablePlugins(SbtTwirl, ScalatraPlugin)
  .settings(
    name := "ScalatraWithSlick",
    scalaVersion := "2.13.1",
    resolvers += Classpaths.typesafeReleases,
    mainClass in assembly := Some("ScalatraLauncher"),
    libraryDependencies ++= Seq(
      scalatra,
      scalatraTest,
      logback,
      jetty,
      javaxServlet,
      slick,
      h2,
      typesafeConfig
    ),
    assemblyJarName in assembly := "scalatra-demo.jar",
  )


lazy val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs@_*) => MergeStrategy.discard
    case PathList("org", "apache", "spark", xs@_*) => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

lazy val assemblySettingsHDFS = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs@_*) => MergeStrategy.discard
    case "application.conf" => MergeStrategy.concat
    case x => MergeStrategy.first
  }
)
