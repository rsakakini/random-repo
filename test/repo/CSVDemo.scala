package repo

import models.Airport

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.io.BufferedSource
import scala.util.Try

/**
  * Created by Ric on 11/02/2017.
  */
object CSVDemo extends App {
  println("Month, Income, Expenses, Profit")
  val bufferedSource: BufferedSource = {
    scala.io.Source.fromFile("/activator-play-slick-angularjs/activator-play-slick-angularjs/test/resources/airports.csv")
  }

  val rows = ArrayBuffer[Array[String]]()

  val airportIt= bufferedSource.getLines.foreach { (line: String) =>
    rows += line.split(",").map(_.trim)
  }

  rows.map(tmp =>
    println(tmp(0) + tmp(1)+ tmp(2)+ tmp(3)+ tmp(8)+ Try(tmp(14)).getOrElse(""))
  )

  /*
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",").map(_.trim)
    // do whatever you want with the columns here
    println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
  }
  */
  bufferedSource.close


}
