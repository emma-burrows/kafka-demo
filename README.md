# kafka-demo
Scala apps demonstrating the basics of Kafka Producer, Consumer, Streams and Connect APIs.

In order to use these projects, you'll need Scala 2.11 and SBT, as well as a running Kafka installation (https://github.com/wurstmeister/kafka-docker worked very nicely for me).

## 00 - Random image generator
Project `00-random-image` contains a little library that generates random images and includes utilities to write them to disk. Run `sbt publish-local` to build it and publish it to your local Ivy cache for ease of use by the other projects.

## 01 - Producer
This uses the random image generator to generate images every few seconds and publish them to a topic called `demo-image`.

## 02 - Consumer
This consumes messages from a topic whose name is passed in as the first command line argument.

    sbt run demo-image
    
It will write the image files it reads out of Kafka to the filesystem so you can have a look at them.

## 03 - Streams
This reads images off the `demo-image` 

## 04 - Connect
This is specifically a sink which writes to S3. You'll need to set the bucket and prefix you want to use.
