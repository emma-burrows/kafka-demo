# kafka-demo
Scala apps demonstrating the basics of Kafka Producer, Consumer, Streams and Connect APIs.

In order to use these projects, you'll need Scala 2.13 and SBT, as well as a running Kafka installation (https://github.com/wurstmeister/kafka-docker worked very nicely for me).

## 00 - Random image generator
Project `00-random-image` contains a little library that generates random images and includes utilities to write them to disk. To publish it locally:
 
    cd 00-random-image
    sbt publishLocal
    
This will build and publish it to your local Ivy cache for ease of use by the other projects.

## 01 - Producer
This uses the random image generator to generate images every few seconds and publish them to a topic (in this case `demo-image`):
 
    cd 01-producer
    sbt "run demo-image"

## 02 - Consumer
This consumes messages from a topic whose name is passed in as the first command line argument (in this case `demo-image`).

    cd 02-consumer
    sbt "run demo-image"
    
It will write the image files it reads out of Kafka to the filesystem so you can have a look at them.

## 03 - Streams
This reads images off the specified topic and overlays them with another image, then produces the result to a Kafa topic called `demo-kafka-image`:

    cd 03-streams
    sbt run demo-image

## 04 - Connect
This is a Kafka Connect sink which reads the modified image from the topic in 03 and writes it to an S3 bucket. 
You'll need to set the bucket and prefix you want to use.
