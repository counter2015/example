# Kafka SparkStreaming Redis example
Kafka: https://kafka.apache.org

This example depends on version 2.0.1.

The project struct like this
```shell
[root@mycentos kafka-streaming-redis]# tree
.
└── src
    └── main
        ├── resources
        │   └── application.conf
        └── scala
            └── com
                └── gihub
                    └── counter2015
                        ├── ClusterStreaming.scala
                        └── ConfigInstance.scala


```

## Requirement 
- Kafka 
- Zookeeper
- Redis Cluster
- Spark


## Build
```shell
$ sbt "project kafka_streaming_redis" assembly
# you will see something like this
[info] Assembly up to date: /root/dev/example/kafka-streaming-redis/target/scala-2.11/kafka-spark-redis-example.jar
[success] Total time: 5 s, completed 2019-2-14 17:56:07
```

## Run
```shell 
# Start Zookeeper
$ zkServer.sh start

# Start Kafka
$ ${CONFLUENT_HOME}/bin/kafka-server-start -daemon ${CONFLUENT_HOME}/etc/kafka/server.properties
```

```shell 
# for the details of deploying Redis Cluster, Please reference Redis documentation.
# https://redis.io/topics/cluster-tutorial
[root@mycentos cluster-test]# ls
  7000  7001  7002  7003  7004  7005  redis-cli  redis-server  redis-trib.rb  run.sh
[root@mycentos cluster-test]# cat run.sh
  cd 7000
  ../redis-server ./redis.conf
  cd ../7001
  ../redis-server ./redis.conf
  cd ../7002
  ../redis-server ./redis.conf
  cd ../7003
  ../redis-server ./redis.conf
  cd ../7004
  ../redis-server ./redis.conf
  cd ../7005
  ../redis-server ./redis.conf
[root@mycentos cluster-test]#  ./run.sh
```


```shell
# Submit jar to spark
$ spark-submit kafka-spark-redis-example.jar

# Meanwhile, you can open another two console to test the program.


$ bin/kafka-console-producer --broker-list mycentos:9092 --topic test1
> 3

$ bin/kafka-console-consumer --bootstrap-server mycentos:9092 --topic test1 --from-beginning
3

# the data store in Redis Cluster
[root@mycentos cluster-test]# ./redis-cli -p 7001 get 3
(error) MOVED 1584 127.0.0.1:7000
[root@mycentos cluster-test]# ./redis-cli -p 7000 get 3
"1"
```

