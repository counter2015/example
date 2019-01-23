# Scala-logging example
Scala-logging: https://github.com/lightbend/scala-logging

This example use [logback](https://github.com/qos-ch/logback) as logging backend.

The project struct like this
```shell
.
├── README.md
└── src
    └── main
        ├── resources
        │   └── logback.xml
        └── scala
            └── com
                └── github
                    └── counter2015
                        └── Main.scala

```
`logback.xml` in `src/main/resources` is the default config file of logback. And `Main.scala` in `src/main/scala/com/gihub/counter2015` is the program entry.

In this example, there are two `Appender`, 
- `ConsoleAppender`, the log will output to  console.
- `FileAppender`, the log will output to local file system.

For more detail. you can look up the `logback.xml`.

## Run
```shell
$ git clone https://github.com/counter2015/example.git
$ cd example
```
you can just run it through `sbt`
```shell
$ sbt 
sbt:global> project logging
stb:LoggingExample> run
```
or you can build a fat-jar
```shell
$ sbt "project logging" assembly
```
the complied jar store at `scala-logging/target/scala-2.11/scala-logging-example.jar`
```shell
$ cd scala-logging/target/scala-2.11/
$ java -jar scala-logging-example.jar 
```
you will see something like this
```shell
07:03:44.334 [main] INFO  com.github.counter2015.Main$ - This is convenient, strange!
Logger info done.
Log file store in "logs" directory.
```
and in the `logs/` you will find the log file `example.log`
```shell
$ cat logs/example.log 
07:03:44.334 [main] INFO  com.github.counter2015.Main$ - This is convenient, strange!
```