import com.typesafe.config.{Config, ConfigFactory}

object ConfigInstance {
  val config: Config = ConfigFactory.parseResources("application.conf")
}

