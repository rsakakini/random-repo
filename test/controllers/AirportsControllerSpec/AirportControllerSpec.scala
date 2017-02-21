package controllers.AirportsControllerSpec

import controllers.{AirportController, WebJarAssets}
import models.Airport
import org.specs2.execute.Results
import org.specs2.mock.Mockito
import play.api.test.{FakeRequest, PlaySpecification}
import services.FileLoaderService

import scala.concurrent.Future

class AirportControllerSpec  extends PlaySpecification with Mockito with Results {

  val mockedRepo = mock[FileLoaderService]
  val mockedWebJarAssets = mock[WebJarAssets]
  val airportController= new AirportController(mockedRepo,mockedWebJarAssets)

  "AirportController " should {


    "get all list" in {

      //[{"id":"6523","ident":"00A","type_":"heliport","name":"Total Rf Heliport","iso_country":"US"}]
      //Airport(id: String, ident: String, type_ : String, name: String, iso_country: String)
      val emp = Airport("6523","00A","heliport","Total Rf Heliport","US")
      mockedRepo.getAirportsData returns Future.successful(List(emp))
      val result = airportController.listAirport.apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"success","data":[{"id":"6523","ident":"00A","type_":"heliport","name":"Total Rf Heliport","iso_country":"US"}],"msg":""}"""
    }

  }

}

