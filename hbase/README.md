# HBase example
HBase: https://hbase.apache.org

This example depends on version 2.1.3.

The project struct like this
```shell
.
├── README.md
└── src
    └── main
        └── scala
            └── com
                └── github
                    └── counter2015
                        └── PutAndGet.scala
```

## Requirement 
- Hadoop 2.7.1
- Zookeeper
- HBase 2.1.3
 
you should firstly run HBase, in this example, I have started HBase at 192.168.22.129
then in HBase shell 
```shell
# if not start HBase
$ start-hbase.sh

$ hbase shell
# create a namespace `test`
hbase> create_namespace 'test'

# create a table `test_table_2` in namespace `test` with a column famliy `column family 1`
hbase> create 'test:test_table_2', 'column_family_1'
```


## Run
```shell
$ sbt
sbt:global> project hbase
sbt:HbaseExample> run
log4j:WARN No appenders could be found for logger (org.apache.hadoop.util.Shell).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
value_1
[success] Total time: 6 s, completed Mar 21, 2019 12:23:02 PM
```

you can find it by HBase shell
```shell
$ hbase shell
hbase> scan 'test:test_table_2'
ROW                                          COLUMN+CELL                                                                                                                      
 rowkey1                                     column=column_family_1:column_1, timestamp=1553169563988, value=value_1                                                          
1 row(s)
Took 0.0391 seconds 
```