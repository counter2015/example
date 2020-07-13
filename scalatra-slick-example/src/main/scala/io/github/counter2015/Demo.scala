package io.github.counter2015

import io.github.counter2015.Tables.{Routes, _}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Demo {

  def main(args: Array[String]): Unit = {
    val db = Database.forConfig("h2mem1")

    val routes = TableQuery[Routes]
    val sortById = routes.sortBy(_.id.desc)
    val statistics = routes
      .groupBy(_.areaId)
      .map { case (areaId, rs) =>
        (areaId,
          rs.length,
          rs.map(_.longitude).min,
          rs.map(_.longitude).max,
          rs.map(_.latitude).min,
          rs.map(_.latitude).max)
      }

    def bySuffix(str: String) = {
      routes.filter(_.routeName.toLowerCase like f"%%${str.toLowerCase}")
        .groupBy(_.areaId).map(_._1)
    }

    val areasWithTrails = areas.filter(_.id in bySuffix("l"))

    val crossJoin = areas join routes
    val innerJoin = routes join areas on (_.areaId === _.id)
    val leftJoin = areas joinLeft routes on (_.id === _.areaId)

    val trailAreasQuery = for {
      route <- routes
      if route.routeName.toLowerCase like "%trail"
      area <- route.area
    } yield (route, area)


    def log(title: String)(xs: Seq[Any]): Unit = {
      println(f"$title")
      xs foreach { x => println(f"--$x") }
      println
    }


    val action = for {
      _ <- DbSetup.createDatabase
      _ <- routes.lessRoutes.result map log("limited routes")
      _ <- routes.sortById.result map log("sorted routes (id)")
      _ <- routes.sortByIdName.result map log("sorted routes (id, name)")
      _ <- routes.statistics.result map log("area statistics")
      _ <- DbSetup.dropDatabase
    } yield ()


    Await.result(db.run(action), Duration(1, "second"))
  }
}
