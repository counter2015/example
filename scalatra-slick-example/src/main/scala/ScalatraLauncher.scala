import io.github.counter2015.AppConfig
import org.eclipse.jetty.server.{Server, ServerConnector}
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object ScalatraLauncher {
  def main(args: Array[String]): Unit = {
    val conf = AppConfig.load
    val server = new Server
    server.setStopTimeout(5000)
    server.setStopAtShutdown(true)


    val connector = new ServerConnector(server)
    connector.setHost("127.0.0.1")
    connector.setPort(conf.port)

    server.addConnector(connector)

    val webAppContext = new WebAppContext
    webAppContext.setContextPath("/")
    webAppContext.setResourceBase("src/main/webapp")
    webAppContext.setEventListeners(Array(new ScalatraListener))

    server.setHandler(webAppContext)

    server.start()
    server.join()
  }
}