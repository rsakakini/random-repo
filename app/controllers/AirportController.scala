package controllers

import com.google.inject.Inject
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import services.FileLoaderService
import utils.Constants
import utils.JsonFormat._


class AirportController @Inject()(fileLoader: FileLoaderService, webJarAssets: WebJarAssets)  extends Controller {

  import Constants._

  val logger = Logger(this.getClass())

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }


  def listAirport: Action[AnyContent] = Action.async {
    fileLoader.getAirportsData.map { res =>
       Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def listAirportWithCountryName: Action[AnyContent] = Action.async {
    fileLoader.getAirportsWithCountryName.map { res =>
      Ok(successResponse(Json.toJson(res), ""))
    }

  }


  def maxNumberAirport: Action[AnyContent] = Action.async {
    fileLoader.maxNumberAirport.map { res =>
      Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def typeOfRunways: Action[AnyContent] = Action.async {
    fileLoader.typeOfRunways.map { res =>
      Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def queries(): Action[AnyContent] = Action {
    Ok(views.html.query(webJarAssets))

  }

  def runwaysQuery(): Action[AnyContent] = Action {
    Ok(views.html.runwaysquery(webJarAssets))

  }

  def countriesQuery(): Action[AnyContent] = Action {
    Ok(views.html.countriesquery(webJarAssets))

  }

  def mostCommonRunWaysQuery: Action[AnyContent] = Action.async {
    fileLoader.mostCommonRunWays.map { res =>
      Ok(successResponse(Json.toJson(res), ""))
    }

  }

  def search(): Action[AnyContent] = Action {
    Ok(views.html.search(webJarAssets))

  }

}



