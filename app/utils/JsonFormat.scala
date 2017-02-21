package utils

import models._
import play.api.libs.json.Json

object JsonFormat {

  implicit val airportFormat = Json.format[Airport]

  implicit val countriesFormat = Json.format[Countries]

  implicit val runwaysFormat = Json.format[RunWays]

  implicit val airportAndRunWaysFormat = Json.format[AirportAndRunWays]

  implicit val runWaysPerCountryFormat = Json.format[RunWaysPerCountry]

  implicit val airportWithCountryNameFormat = Json.format[AirportWithCountryName]

  implicit val mostCommonRunWays = Json.format[MostCommonRunWays]




}


