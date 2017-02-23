libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "org.slf4j" % "slf4j-simple" % "1.7.22",
  "org.apache.kafka" % "kafka-clients" % "0.10.0.1",

  // From local Ivy repo
  "demo-random-image" % "demo-random-image_2.11" % "1.0.0"
)
