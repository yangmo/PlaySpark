name := """Spark and Play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.1"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.5.1"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator
