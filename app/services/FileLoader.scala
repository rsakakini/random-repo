package services

import javax.inject._

import models._
import play.Play
import play.api.inject.ApplicationLifecycle
import play.api.{Configuration, Logger}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}
import scala.concurrent.duration._
import scala.collection.immutable.{Seq, Iterable, ListMap}


@Singleton
class FileLoader @Inject()(protected val config: Configuration) {

  val logger = Logger(this.getClass())
  logger.info("Airports Starting.........")

  val getAirportsFile : List[Airport] = {
    val airports: String = config.underlying.getString("files.airports")

    logger.info("airports.....")

    val airportsBufferedSource = Source.fromFile(airports,"ISO-8859-1") //Play.application().resourceAsStream(airports,"ISO-8859-1")//


    val airportIt: List[Airport] = airportsBufferedSource.getLines.map { line =>
      val tmp: Array[String] = line.split(",").map(_.replace("\"","").trim)
      Airport(tmp(0), tmp(1), tmp(2), tmp(3), tmp(8))
    }.toList.filter(air => air.id!="id")

    airportsBufferedSource.close

    airportIt
  }

  val getCountriesFile: List[Countries] ={

    val countries: String = config.underlying.getString("files.countries")

    logger.info("countries.....")

    var countriesBufferedSource = Source.fromFile(countries,"ISO-8859-1")  //Source.fromInputStream(Play.application().resourceAsStream(countries),"ISO-8859-1")

     val countriesIt = countriesBufferedSource.getLines.map { line =>
      val tmp: Array[String] = line.split(",").map(_.replace("\"","").trim)
      Countries(tmp(0), tmp(1),tmp(2), tmp(3),None)
    }.toList

    countriesBufferedSource.close

    countriesIt

  }

  val getRunwaysFile: List[RunWays] ={

    val runways: String = config.underlying.getString("files.runways")

    logger.info("runways..........")
    val runwaysBufferedSource = Source.fromFile(runways,"ISO-8859-1")  //Source.fromInputStream(Play.application().resourceAsStream(runways),"ISO-8859-1")

    val runwaysIt = runwaysBufferedSource.getLines.map { line =>
      val tmp: Array[String] = line.split(",").map(_.replace("\"","").trim)
      if(tmp.length >= 10)   RunWays(tmp(0), tmp(1), tmp(2), tmp(5), Some(tmp(9)))
      else RunWays(tmp(0), tmp(1), tmp(2), tmp(5), None)
    }.toList

    runwaysBufferedSource.close

    runwaysIt
  }

  def getAirportsData: Future[List[Airport]] = Future({
   getAirportsFile
  })

  def getCountriesData: Future[List[Countries]] =Future({
    getCountriesFile
  })

  def getRunwaysData: Future[List[RunWays]] =Future({
    getRunwaysFile
  })

  def maxNumberAirport: Future[List[Countries]] = Future({
    val airportsGroup = getAirportsFile.groupBy(x => x.iso_country)

    //do not sort here
    val sorted: ListMap[String, Int] = ListMap(airportsGroup.toSeq.sortBy(_._2.size):_*).map(x => (x._1,x._2.size)) //ListMap(map.toSeq.sortBy(_._2.size):_*).map(x => (x._1,x._2.size))//.map((code,list) => (code,list))

    val countries: List[Countries] = getCountriesFile

    val countr: ListMap[List[Countries], Int] = sorted.map(x => (countries.filter(country => x._1 ==country.code),x._2))

    val ret = countr.flatMap(x => x._1.map(country => Countries(country.id, country.code, country.name, country.continent,Some(x._2))))

    ret.toList.sortWith(_.count.getOrElse(0) > _.count.getOrElse(0))
  })


  def typeOfRunways: Future[List[RunWaysPerCountry]] = Future({

    val airportMap: Map[(String), List[Airport]] = getAirportsFile.groupBy(x => (x.iso_country))

    val run = getRunwaysFile

    val runways = getRunwaysFile

    val ret: List[RunWaysPerCountry] = airportMap.map(tupl => RunWaysPerCountry(
      tupl._1,
      tupl._2.flatMap(air => runways.filter(run => run.airport_ref==air.id)).map(runways => runways.surface).toSet
    )
    ).toList

    ret
  })



  def search(what : String)= Future({

    val filterCountries: List[Countries] = getCountriesFile.filter(a => (a.name==what || a.code==what))

    val airport = getAirportsFile

    val runways = getRunwaysFile

    val airport1: List[Airport] = airport.filter(x => x.iso_country==filterCountries.headOption.getOrElse(Countries("", "", "", "", None)).code)

    val res= airport1.map(air => AirportAndRunWays(air.name,runways.filter(run => run.airport_ref==air.id ).map(ru => ru.surface).toSet))

    res
})


  def getAirportsWithCountryName: Future[List[AirportWithCountryName]] = Future({

    val countries = getCountriesFile

    val airports = getAirportsFile

    val res = airports.map { air =>
      AirportWithCountryName(air.id, air.ident, air.type_, air.name, countries.filter(country => air.iso_country == country.code).headOption.getOrElse(Countries("", "", "", "", None)).name,air.iso_country)
    }

    res
  })


}
