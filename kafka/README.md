# Kafka client example
Kafka: https://kafka.apache.org

This example depends on version 2.0.1.

The project struct like this
```shell
.
├── README.md
└── src
    └── main
        ├── resources
        └── scala
            └── com
                └── github
                    └── counter2015
                        ├── SimpleConsumer.scala
                        └── SimpleProducer.scala

```

## Requirement 
- kafka 2.0 +
- Zookeeper

## Properties
- `bootstrap.servers` here is "192.168.22.129:9092", where "192.168.22.129" is the ip of ***my*** kafka machine,and "9092" is the default of kafka.
- `topic` here topic name is `testTopic`

you should first start `zookeeper` and `kafka` first, be sure you have config `listeners` and `zookeeper.connect` in kafka config file `server.properties`

for more information, see:
https://kafka.apache.org/documentation/#producerconfigs
https://kafka.apache.org/documentation/#consumerconfigs

## Run
you can build a fat-jar
```shell
$ sbt "project kafka" assembly
```
the complied jar store at `kafka/target/scala-2.11/kafka-example.jar`
start a consumer in console
```shell
$ cd kafka/target/scala-2.11/
$ java -cp kafka-example.jar com.github.counter2015.SimpleConsumer 
```
start a producer in another console

```shell
$  java -cp kafka-example.jar com.github.counter2015.SimpleProducer
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```
you will see something like this in another console
```shell
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
offset = 32, key = null, value = {"id":"1", "answer":"3", "pass":"no"}

offset = 33, key = null, value = {"id":"1", "answer":"7", "pass":"yes"}

offset = 34, key = null, value = {"id":"2", "answer":"42", "pass":"no"}

offset = 35, key = null, value = {"id":"3", "answer":"21", "pass":"no"}

```