package com.github.counter2015

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object SimpleProducer {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.22.129:9092")
    props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val messages =
      """
        |{"id":"1", "answer":"3", "pass":"no"}
        |{"id":"1", "answer":"7", "pass":"yes"}
        |{"id":"2", "answer":"42", "pass":"no"}
        |{"id":"3", "answer":"21", "pass":"no"}""".trim.stripMargin.split("\n")

    val topic = "testTopic"
    messages.foreach(message => {
      val record = new ProducerRecord[String, String](topic, message)
      producer.send(record)
    })
    producer.close()
  }
}
