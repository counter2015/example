import io.github.counter2015.{DbSetup, MyScalatraServlet}
import javax.servlet.ServletContext
import org.scalatra._
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ScalatraBootstrap extends LifeCycle {

  // read config from default path `src/main/resources/application.conf`, using typesafe config library
  // see https://github.com/lightbend/config
  val db = Database.forConfig("h2mem1")
  val app = new MyScalatraServlet(db)

  override def init(context: ServletContext): Unit = {
    val res = db.run(DbSetup.createDatabase)
    Await.result(res, Duration(5, "seconds"))
    context.mount(new MyScalatraServlet(db), "/*")
  }

  override def destroy(context: ServletContext): Unit = {
    db.close()
  }
}
