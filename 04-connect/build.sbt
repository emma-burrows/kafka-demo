scalaVersion := "2.13.1"

resolvers ++= Seq(
  "confluent" at "http://packages.confluent.io/maven/",
  "conjars" at "http://conjars.org/repo"
)

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "org.apache.kafka" % "kafka-clients" % "2.4.0",
  "org.apache.kafka" % "connect-runtime" % "2.4.0",
  "org.apache.kafka" % "connect-json" % "2.4.0",
  "io.confluent" % "kafka-connect-s3" % "5.0.0",

  // interact with S3
//  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.1",

  // From local Ivy repo
  "demo-random-image" %% "demo-random-image" % "1.2.0"
)
