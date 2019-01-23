package com.github.counter2015

import com.typesafe.scalalogging.LazyLogging

object Main extends LazyLogging{

  def main(args: Array[String]){
    logger.info("This is convenient, strange!")
    println("Logger info done.\nLog file store in \"logs\" directory.")
  }
}
