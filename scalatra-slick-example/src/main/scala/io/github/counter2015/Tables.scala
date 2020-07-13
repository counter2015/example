package io.github.counter2015

import slick.jdbc.H2Profile.api._

object Tables {

  class Areas(tag: Tag) extends Table[Area](tag, "AREAS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def location = column[String]("LOCATION")

    def latitude = column[Double]("LATITUDE")

    def longitude = column[Double]("LONGITUDE")

    def description = column[String]("DESCRIPTION")

    def * = (id, name, location, longitude, latitude, description) <> (Area.tupled, Area.unapply)
  }

  class Routes(tag: Tag) extends Table[Route](tag, "ROUTES") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

    def areaId = column[Int]("AREAID")

    def mountainName = column[Option[String]]("MOUNTAINNAME")

    def routeName = column[String]("ROUTENAME")

    def description = column[String]("DESCRIPTION")

    def latitude = column[Double]("LATITUDE")

    def longitude = column[Double]("LONGITUDE")

    def * = (id, areaId, mountainName, routeName, latitude, longitude, description) <> (Route.tupled, Route.unapply)

    def area = foreignKey("FK_ROUTE_AREA", areaId, areas)(_.id)

  }

  // table queries

  val areas = TableQuery[Areas]

  val routes = TableQuery[Routes]

  def allAreas: DBIO[Seq[Area]] = areas.result


  implicit class RoutesQueryExtensions[C[_]](query: Query[Routes, Route, C]) {

    val lessRoutes = query.drop(3).take(10)

    val sortById = query.sortBy(_.id.desc)
    val sortByIdName = query.sortBy(r => (r.areaId.asc, r.routeName))

    val statistics = query
      .groupBy(_.areaId)
      .map { case (areaId, rs) =>
        (areaId,
          rs.length,
          rs.map(_.longitude).min,
          rs.map(_.longitude).max,
          rs.map(_.latitude).min,
          rs.map(_.latitude).max)
      }

    def byId(id: Int): Query[Routes, Route, C] = query.filter(_.id === id)

    def byName(name: String): Query[Routes, Route, C] = query.filter(_.routeName === name)

    def bySuffix(str: String): Query[Routes, Route, C] =
      query.filter(_.routeName.toLowerCase like f"%%${str.toLowerCase}")

    val distinctAreaIds = query.groupBy(_.areaId).map(_._1)

    val withAreas = query join areas on (_.areaId === _.id)


  }

}
