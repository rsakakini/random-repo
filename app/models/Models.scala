package models

case class Airport(id: String, ident: String, type_ : String, name: String, iso_country: String) //, local_code: String)

case class AirportWithCountryName(id: String, ident: String, type_ : String, name: String, countryName: String,iso_country: String) //, local_code: String)

case class RunWays(id: String, airport_ref: String, airport_ident : String, surface: String, le_ident: Option[String])

case class Countries(id: String, code: String, name : String, continent: String, count: Option[Int]=None)

case class AirportAndRunWays(name: String, surface: Set[String])

case class RunWaysPerCountry(iso_country: String, surface: Set[String])

case class MostCommonRunWays(name: String, number: Int)