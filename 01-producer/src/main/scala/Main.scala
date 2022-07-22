import java.util
import org.apache.kafka.clients.producer._
import scala.jdk.CollectionConverters._

object Main {
  def main(args: Array[String]) {
    val topicName = args.head
    val producer = new KafkaProducer[String, Array[Byte]](config)

    for(i <- 0 until 5) {
      println(s"Generating file $i for $topicName")

      val img = new RandomImage().generateImageByteArray()
      producer.send(new ProducerRecord(topicName, Integer.toString(i), img), new AckCallback(i))

      RandomImage.writeFile(s"file$i", img)
      Thread.sleep(3000)
    }
    producer.close()
  }

  val config: util.Map[String, AnyRef] =
    Map(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG      -> "localhost:9092",
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG   -> "org.apache.kafka.common.serialization.StringSerializer",
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.ByteArraySerializer"
    ).asInstanceOf[Map[String, AnyRef]].asJava
}

class AckCallback(i: Int) extends Callback {
  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
    println(s"Message $i acknowledged successfully at ${metadata.timestamp()} written to offset ${metadata.offset()}")
  }
}
