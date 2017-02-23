libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "org.apache.kafka" % "kafka-clients" % "0.10.0.1",
  "org.apache.kafka" % "connect-runtime" % "0.10.0.1",

  // interact with S3
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.1",

  // From local Ivy repo
  "demo-random-image" % "demo-random-image_2.11" % "1.0.0"
)
