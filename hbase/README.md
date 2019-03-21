# HBase example
Kafka: https://kafka.apache.org

This example depends on version 2.0.1.

The project struct like this
```shell
[tbd]

```

## Requirement 
- Hadoop 2.7.1
- Zookeeper
- HBase 2.1.3
 
you should firstly run HBase, in this example, I hava started HBase at 192.168.22.129
then in hbase shell 
```shell
# create a namespace `test`
hbase> create_namespace 'test'

# create a table `test_table_2` in namespace `test` with a column famliy `column family 1`
hbase> create 'test:test_table_2', 'column_family_1'
```


## Run
[TBD]
