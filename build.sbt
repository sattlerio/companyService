
name := "company-service"
organization := "com.sattlerio"
description := "Wrapper for scheduled and planned tasks"
version := "1.0"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

assemblyJarName in assembly := "company-service.jar"
mainClass in assembly := Some(" com.sattlerio.companyServiceApplication")

libraryDependencies += "io.dropwizard" % "dropwizard-core" % "1.2.2"
libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1212.jre7"
libraryDependencies += "io.dropwizard" % "dropwizard-jdbi" % "1.2.2"
excludeDependencies += "commons-logging" % "commons-logging"
libraryDependencies += "com.mashape.unirest" % "unirest-java" % "1.4.9"


libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test
libraryDependencies += "io.dropwizard" % "dropwizard-testing" % "1.2.2" % Test

testOptions += Tests.Argument(TestFrameworks.JUnit)


