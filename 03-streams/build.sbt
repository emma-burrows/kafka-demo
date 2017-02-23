libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "log4j" % "log4j" % "1.2.17",
  "org.apache.kafka" % "kafka-clients" % "0.10.1.1",
  "org.apache.kafka" % "kafka-streams" % "0.10.1.1",

  // From local Ivy repo
  "demo-random-image" % "demo-random-image_2.11" % "1.0.0"
)
