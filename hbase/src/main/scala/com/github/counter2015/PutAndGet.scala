package com.github.counter2015

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, Put}
import org.apache.hadoop.hbase.util.Bytes

object PutAndGet {
  def main(args: Array[String]): Unit = {
    // create the connection of HBase
    // Note: `192.168.22.129` here is the IP of machine which is running HBase, you can also config it as hostname as you like
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", "192.168.22.129")
    val connection = ConnectionFactory.createConnection(conf)

    // get the table, make the put
    // it assume that you have created namespace `test` and table `test_table_2`
    val table = connection.getTable(TableName.valueOf("test:test_table_2"))
    val theput = new Put(Bytes.toBytes("rowkey1"))
    theput.addColumn(Bytes.toBytes("column_family_1"), Bytes.toBytes("column_1"), Bytes.toBytes("value_1"))
    table.put(theput)

    // make the get, print to screen
    val theget = new Get(Bytes.toBytes("rowkey1"))
    val result = table.get(theget)
    val value = result.value()
    println(Bytes.toString(value))
  }
}
