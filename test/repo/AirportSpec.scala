package repo

import org.specs2.execute.{AsResult, Results}
import play.api.{Application, Configuration}
import play.api.test.{PlaySpecification, WithApplication}
import play.mvc.Result
import services.FileLoaderService
import org.scalatestplus.play.PlaySpec
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.control.Exception.Described

class AirportSpec extends PlaySpec {

  import models._
  //override def described(s: String): Described = super.described(s)

  def fileLoader(implicit app: Application): FileLoaderService = Application.instanceCache[FileLoaderService].apply(app)

  "FileLoader " should  {

    "get all rows from airports" in new WithApplication() {
      val result = await(fileLoader.getAirportsData)
      result.length === 46506
      result.head.name === "Total Rf Heliport"

    }

    "search(what : String)" in new WithApplication() {
      val result = await(fileLoader.search( "BT"))
      result.head.name ===  "[BT]"
      result.head.surface.size === 3
    }


  "get ordered countries by number airport" in new WithApplication() {
   val result = await(fileLoader.maxNumberAirport)
    result.head.code ===  "[US]" && result.head.count.getOrElse(0) === 21476
  }

    "get all rows from countries" in new WithApplication() {
    val result = await(fileLoader.getCountriesData)
    result.length === 247
    result.head.id === 302613

    }


  "get all rows from runways" in new WithApplication() {
   val result = await(fileLoader.getRunwaysData)
   result.length === 39536
    result.head.id === 313663

}

    "get most common runways" in new WithApplication() {
      val result = await(fileLoader.mostCommonRunWays)
      //result.length === 39536
      result.head.name === "ZZZ"
      result.head.number === 1

    }


def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}
}


