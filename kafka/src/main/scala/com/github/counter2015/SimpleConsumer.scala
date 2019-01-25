package com.github.counter2015

import java.time.Duration
import java.util.Properties
import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._

object SimpleConsumer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.22.129:9092")
    props.put("group.id", "test")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList("testTopic"))

    while (true) {
      val records = consumer.poll(Duration.ofMillis(100)).asScala
      for (record <- records) {
        println("offset = %d, key = %s, value = %s%n".format(record.offset(), record.key, record.value))
      }
    }
  }
}
