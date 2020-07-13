# Scalatra example

- Scalatra: https://scalatra.org/
- Slick: https://scala-slick.org/
- Twirl: https://www.playframework.com/documentation/2.8.x/ScalaTemplates


This example is a rewrite of [chapter 10 in Scalatra in Action](https://github.com/scalatra/scalatra-in-action/tree/master/chapter10) 
- update components versions
- fix standalone assert problem

The project struct like this
```shell
└── src
    └── main
        ├── resources
        │   ├── application.conf  // application config
        │   └── logback.xml       // log config
        ├── scala
        │   ├── ScalatraBootstrap.scala
        │   ├── ScalatraLauncher.scala  // main class
        │   └── io
        │       └── github
        │           └── counter2015
        │               ├── AppConfig.scala
        │               ├── DbSetup.scala
        │               ├── Demo.scala // stand demo which can also run
        │               ├── Domain.scala
        │               ├── MyScalatraServlet.scala
        │               ├── Tables.scala
        │               └── UrlShortener.scala
        ├── twirl
        │   ├── layouts
        │   │   └── default.scala.html
        │   └── views
        │       ├── areas.scala.html
        │       └── hello.scala.html
        └── webapp
            ├── WEB-INF
            │   └── web.xml
            └── css
                └── bootstrap.min.css
```

## Run
```shell
$ git clone https://github.com/counter2015/example.git
$ cd example
```
you can just run it through `sbt`
```shell
$ sbt 
sbt:global> project scalatraDemo
stb:ScalatraWithSlick> run
```
or you can build a fat-jar
```shell
$ sbt "project scalatraDemo" assembly
```
the complied jar store at `scalatra-slick-example/target/scala-2.13/scalatra-demo.jar`
```shell
$ cd scalatra-slick-example/target/scala-2.13/
$ java -jar scalatra-demo.jar 
```

you will see something like this in http://localhost:8080/areas

![](https://counter2015.com/picture/scalatra-10-1.jpg)