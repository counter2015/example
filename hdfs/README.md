# A simple HDFS client

## Requirement 
- Hadoop



## Build
```shell
$ sbt "project hdfs" assembly

# you will see something like
[info] Packaging ....example/hdfs/target/scala-2.11/simple-hdfs-client.jar ...
[info] Done packaging.
```


## Run
Suppose you have a hadoop instance run at "hdfs://mycentos:9000"

```shell 
$ java -jar simple-hdfs-client.jar --help

A simple implementation of hdfs shell commands
  --copyFromLocal [local file name] [hdfs path]
    copy `local file name` to `hdfs path`

  --cat [hdfs path]
    output HDFS file content to console

  --get [hdfs path] [local path]
    get HDFS file to local file system.

  --describe [hdfs path]
    show HDFS file content

  --removeFile | -rmf [hdfs path]
    delete a HDFS file

  --removeDir | -rmd [hdfs path]
    delete a HDFS dir

  --append | -ap [hdfs path]
    append content to HDFS file

  --move | -mv [source hdfs path] [target hdfs path]
    rename a HDFS file

  --help | -h
    print this help message

```