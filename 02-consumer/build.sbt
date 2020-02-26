scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "org.slf4j" % "slf4j-simple" % "1.7.22",
  "org.apache.kafka" % "kafka-clients" % "2.4.0",

  // From local Ivy repo
  "demo-random-image" %% "demo-random-image" % "1.2.0"
)
