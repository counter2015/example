# Custom Prometheus Exporter Example

## Project struct
just a simple scala file.

## Build
```shell 
$ sbt "project prometheusExporters" assembly
# you will see something like this
[info] Assembly up to date: /root/dev/example/prometheus-exporters/target/scala-2.11/my-prometheus-exporters.jar
[success] Total time: 1 s, completed May 15, 2019 8:25:00 AM
```


## Run
```shell
$ java -jar my-prometheus-exporters.jar 
server start at port 9090
counter value now is: 3.0
counter value now is: 7.0
counter value now is: 7.0
counter value now is: 8.0
counter value now is: 12.0
counter value now is: 14.0
counter value now is: 16.0
counter value now is: 18.0
counter value now is: 19.0
counter value now is: 22.0
counter value now is: 22.0
...
```

you can also inspect it by `curl` or browser
```shell 
$ curl localhost:9090/metrics
# HELP scala_my_gauge this is my gauge
# TYPE scala_my_gauge gauge
scala_my_gauge -4.0
# HELP scala_my_summary this is my summary
# TYPE scala_my_summary summary
scala_my_summary_count 20.0
scala_my_summary_sum 46.0
# HELP scala_my_histogram this is my histogram
# TYPE scala_my_histogram histogram
scala_my_histogram_bucket{le="0.005",} 7.0
scala_my_histogram_bucket{le="0.01",} 7.0
scala_my_histogram_bucket{le="0.025",} 7.0
scala_my_histogram_bucket{le="0.05",} 7.0
scala_my_histogram_bucket{le="0.075",} 7.0
scala_my_histogram_bucket{le="0.1",} 7.0
scala_my_histogram_bucket{le="0.25",} 7.0
scala_my_histogram_bucket{le="0.5",} 7.0
scala_my_histogram_bucket{le="0.75",} 7.0
scala_my_histogram_bucket{le="1.0",} 11.0
scala_my_histogram_bucket{le="2.5",} 17.0
scala_my_histogram_bucket{le="5.0",} 20.0
scala_my_histogram_bucket{le="7.5",} 20.0
scala_my_histogram_bucket{le="10.0",} 20.0
scala_my_histogram_bucket{le="+Inf",} 20.0
scala_my_histogram_count 20.0
scala_my_histogram_sum 27.0
# HELP scala_my_counter this is my counter
# TYPE scala_my_counter counter
scala_my_counter 38.0

```