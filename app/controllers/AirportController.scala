package controllers

import com.google.inject.Inject
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import services.FileLoader
import utils.Constants
import utils.JsonFormat._


/**
  * Handles all requests related to airport
  */
class AirportController @Inject()(fileLoader: FileLoader,webJarAssets: WebJarAssets)  extends Controller {

  import Constants._

  val logger = Logger(this.getClass())

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }
  /**
    * Handles request for getting all employee from the database
    */
  /*
  def listAirport(): Action[AnyContent] = Action.async {
    val a= Future(fileLoader.airportsData).map { res =>
     // logger.info("Emp list: " + res)
      Ok(Json.toJson(res))
    }
    a
  }
  */

  def listAirport: Action[AnyContent] = Action.async {
    fileLoader.getAirportsData.map { res =>
      //logger.info("Airport list: " + res)
       Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def listAirportWithCountryName: Action[AnyContent] = Action.async {
    fileLoader.getAirportsWithCountryName.map { res =>
      //logger.info("Airport list: " + res)
      Ok(successResponse(Json.toJson(res), ""))
    }

  }




  def maxNumberAirport: Action[AnyContent] = Action.async {
    fileLoader.maxNumberAirport.map { res =>
     // logger.info("Airport list: " + res)
      Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def typeOfRunways: Action[AnyContent] = Action.async {
    fileLoader.typeOfRunways.map { res =>
      logger.info("Airport list: " + res)
      Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def queries(): Action[AnyContent] = Action {
    Ok(views.html.query(webJarAssets))

  }

  def runwaysquery(): Action[AnyContent] = Action {
    Ok(views.html.runwaysquery(webJarAssets))

  }

  def countriesquery(): Action[AnyContent] = Action {
    Ok(views.html.countriesquery(webJarAssets))

  }

  def search(): Action[AnyContent] = Action {
    Ok(views.html.search(webJarAssets))

  }

}



