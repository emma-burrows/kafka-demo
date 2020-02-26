scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "log4j" % "log4j" % "1.2.17",
  "org.apache.kafka" % "kafka-clients" % "2.4.0",
  "org.apache.kafka" % "kafka-streams" % "2.4.0",

  // From local Ivy repo
  "demo-random-image" %% "demo-random-image" % "1.2.0"
)
