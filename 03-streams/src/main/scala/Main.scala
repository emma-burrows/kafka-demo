import java.io.File
import java.util
import javax.imageio.ImageIO

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.{KStreamBuilder, KeyValueMapper}
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}

import scala.collection.JavaConverters._

object Main {
  def main(args: Array[String]) {
    val topicName = args.head
    val overlayImage = RandomImage.convertImageToByteArray(ImageIO.read(new File("kafka.png")))
    val overlayKeyValueMapper = new OverlayKeyValueMapper(overlayImage)

    val builder: KStreamBuilder = new KStreamBuilder
    val input = builder.stream(Serdes.String(), Serdes.ByteArray(), topicName)
    input.map(overlayKeyValueMapper).to("demo-kafka-image")

    val stream: KafkaStreams = new KafkaStreams(builder, new StreamsConfig(config))
    stream.start()
  }

  val config: util.Map[String, AnyRef] =
    Map(
      StreamsConfig.APPLICATION_ID_CONFIG    -> "Emma's awesome demo step 3",
      StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
      StreamsConfig.ZOOKEEPER_CONNECT_CONFIG -> "localhost:2181",
      StreamsConfig.KEY_SERDE_CLASS_CONFIG   -> Serdes.String.getClass.getName,
      StreamsConfig.VALUE_SERDE_CLASS_CONFIG -> Serdes.ByteArray.getClass.getName
    ).asInstanceOf[Map[String, AnyRef]].asJava


}

class OverlayKeyValueMapper(overlayImage: Array[Byte])
  extends KeyValueMapper[String, Array[Byte], KeyValue[String, Array[Byte]]] {
  override def apply(key: String, value: Array[Byte]) = {
    println(s"Converting message $key")
    new KeyValue(key, RandomImage.overlayImageByteArrays(value, overlayImage))
  }
}
