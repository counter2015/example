package com.gihub.counter2015

import java.util
import java.util.Date

import kafka.serializer.StringDecoder
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import redis.clients.jedis.{HostAndPort, JedisCluster}
import ConfigInstance.config

import scala.collection.JavaConverters._

object ClusterStreaming {
  /**
    * this checkpoint Path is set for debug,
    * for real use,
    * please replace it as valid directory.
     */

  val checkpointPath: String = "file:///root/data/checkpoint/test" + new Date().getTime.toString

  def initSparkStreamingContext(): StreamingContext = {

    // Spark config
    val sparkConf = new SparkConf()
      .setAppName(config.getString("spark.myAppName"))

    val ssc = new StreamingContext(sparkConf, Seconds(config.getInt("spark.appInterval")))
    ssc.checkpoint(checkpointPath)


    // Kafka config
    val topics = config.getString("kafka.topics")
    val topicSet = topics.split(",").toSet
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> config.getString("kafka.brokers")
    )

    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicSet
    )

    /**
      *   flatMap is a one-to-many DStream operation that creates a new DStream
      * by generating multiple new records from each record in the source DStream.
      *   while flatMap will convert Map[String, String] to Map[String, char], so we
      * add a OPTION Some to retain String struct.
      */

    val events = kafkaStream.flatMap(line => Some(line._2))

    events.foreachRDD(rdd => {
      rdd.foreachPartition(partition => {
        val poolConfig = new GenericObjectPoolConfig()

        // Before borrow a instance from pool, do validate operation advance.
        poolConfig.setTestOnBorrow(true)

        // The maximum number of connections allowed across all routes.
        poolConfig.setMaxTotal(config.getInt("redis.pool.maxTotal"))

        // The maximum number of idle connections in the pool.
        poolConfig.setMaxIdle(config.getInt("redis.pool.maxIdle"))

        poolConfig.setMinIdle(config.getInt("redis.pool.minIdle"))

        // The maximum wait time of borrow a instance from pool.
        poolConfig.setMaxWaitMillis(config.getInt("redis.pool.maxWaitInMs"))

        val hostPortSet = new util.HashSet[HostAndPort]()
        val ports = config.getIntList("redis.cluster.ports").asScala.toList

        ports.map(port => new HostAndPort(config.getString("redis.cluster.defaultHost"), port))
          .foreach(hostPort => hostPortSet.add(hostPort))

        val connection = new JedisCluster(hostPortSet, poolConfig)

        partition.foreach(record => {
          connection.incrBy(record, 1)
        })
        connection.close()
      })
    })
    kafkaStream.print()
    ssc
  }

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(checkpointPath, () => initSparkStreamingContext())
    ssc.start()
    ssc.awaitTermination()
  }
}
