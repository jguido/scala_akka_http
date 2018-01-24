lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.unrlab",
  scalaVersion := "2.12.4",
  test in assembly := {}
)

name := "akka_boot"

version := "0.1"

scalaVersion := "2.12.4"

val versions = Map[String, String](
  "AkkaHttp" -> "10.0.11",
  "AkkaCore" -> "2.5.9"
)

val deps = Seq(
  "joda-time" % "joda-time" % "2.9.5",
  "org.joda" % "joda-convert" % "1.7",
  "ch.megard" %% "akka-http-cors" % "0.2.2",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "net.logstash.logback" % "logstash-logback-encoder" % "4.5.1",
  "com.typesafe" % "config" % "1.3.2",
  "com.typesafe.akka" %% "akka-actor" % versions("AkkaCore"),
  "com.typesafe.akka" %% "akka-stream" % versions("AkkaCore"),
  "com.typesafe.akka" %% "akka-slf4j" % versions("AkkaCore"),
  "com.typesafe.akka" %% "akka-http" % versions("AkkaHttp"),
  "com.typesafe.akka" %% "akka-http-spray-json" % versions("AkkaHttp")
)

val testDeps = Seq(
  "com.typesafe.akka" %% "akka-testkit" % versions("AkkaCore"),
  "com.typesafe.akka" %% "akka-http-testkit" % versions("AkkaHttp"),
  "org.scalatest"     %% "scalatest" % "3.0.4" % "test",
  "com.jayway.restassured" % "rest-assured" % "2.9.0" % "test"
)

libraryDependencies := deps ++ testDeps

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
//    mainClass in assembly := Some("com.unrlab.Application.main"),
    assemblyJarName in assembly := "akka-boot.jar"
  )

