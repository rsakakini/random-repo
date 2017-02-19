name := """Airport"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  // WebJars (i.e. client-side) dependencies
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "jquery" % "1.11.3",
  "org.webjars" % "bootstrap" % "3.3.6" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.5.5" exclude("org.webjars", "jquery"),
  "org.webjars" % "angular-ui-bootstrap" % "1.3.2" exclude("org.webjars", "jquery"),
  //"org.webjars.bower" % "angular-utils-pagination" % "0.11.1"   exclude("org.webjars.bower", "angular")  exclude("org.webjars", "angularjs") exclude("org.webjars", "jquery"),
  specs2 % Test
)

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
