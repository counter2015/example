import java.io.IOException
import io.prometheus.client.{Counter, Gauge, Histogram, Summary}
import io.prometheus.client.exporter.HTTPServer


object Exporters {
  private def something(minValue: Int, maxValue: Int): Int = {
    val res = minValue + (Math.random() * (maxValue - minValue))
    res.toInt
  }

  def main(args: Array[String]): Unit = {
    val counter = Counter.build()
      .namespace("scala")
      .name("my_counter")
      .help("this is my counter")
      .register()

    val gauge = Gauge.build()
      .namespace("scala")
      .name("my_gauge")
      .help("this is my gauge")
      .register()

    val histogram = Histogram.build()
      .namespace("scala")
      .name("my_histogram")
      .help("this is my histogram")
      .register()

    val summary = Summary.build()
      .namespace("scala")
      .name("my_summary")
      .help("this is my summary")
      .register()

    import scala.language.implicitConversions
    implicit def somthingToRunnable(func: () => Unit): Runnable = new Runnable {
      def run(): Unit = func()
    }


    val bgThread = new Thread(() => {
      def randExporters(): Unit = {
        while (true) try {
          counter.inc(something(0, 5))
          println("counter value now is: " + counter.get())
          gauge.set(something(-5, 10))
          histogram.observe(something(0, 5))
          summary.observe(something(0, 5))
          Thread.sleep(1000)
        } catch {
          case e: InterruptedException =>
            e.printStackTrace()
        }
      }

      randExporters()
    })

    bgThread.start()
    try {
      val server = new HTTPServer(9090)
      println(s"server start at port ${server.getPort}")
    } catch {
      case e: IOException =>
        e.printStackTrace()
    }
  }
}
