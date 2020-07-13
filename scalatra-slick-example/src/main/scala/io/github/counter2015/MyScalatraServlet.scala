package io.github.counter2015

import io.github.counter2015.DbSetup.createArea
import io.github.counter2015.Tables._
import org.scalatra._
import slick.jdbc.H2Profile.backend.Database

import scala.concurrent.{ExecutionContext, Future}

class MyScalatraServlet(db: Database) extends ScalatraServlet with FutureSupport {
  //Mixes in the FutureSupport trait and defines an implicit ExecutionContext for handling the Futures
  override protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  before("/*") {
    contentType = "text/html"
  }

  get("/areas") {

    new AsyncResult() {
      override val is: Future[_] = {
        db.run(allAreas map { areas =>
          views.html.areas(areas)
        })
      }
    }
  }

  get("/") {
    views.html.hello(UrlShortener.nextFreeToken)
  }

  post("/areas") {
    val name = params.get("name").getOrElse(halt(BadRequest()))
    val location = params.get("location") getOrElse halt(BadRequest())
    val latitude = params.getAs[Double]("latitude") getOrElse halt(BadRequest())
    val longitude = params.getAs[Double]("longitude") getOrElse halt(BadRequest())
    val description = params.get("description") getOrElse halt(BadRequest())

    db.run(createArea(name, location, latitude, longitude, description)) map { area =>
      Found(f"/areas/${area.id}")
    }
  }
}
