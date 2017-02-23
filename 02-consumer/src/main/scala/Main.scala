import java.util

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}

import scala.collection.JavaConverters._

object Main {
  def main(args: Array[String]) {
    val topicName = args.head
    val consumer = new KafkaConsumer[String, Array[Byte]](config)
    consumer.subscribe(List(topicName).asJava)

    while(true) {
      val records: ConsumerRecords[String, Array[Byte]] = consumer.poll(100)

      for (record: ConsumerRecord[String, Array[Byte]] <- records.iterator().asScala) {
          println(s"Retrieved message ${record.key()} from $topicName")
          RandomImage.writeFile(s"file${record.key()}", record.value())
        }
      }
  }

  val config: util.Map[String, AnyRef] =
    Map(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG        -> "localhost:9092",
      ConsumerConfig.GROUP_ID_CONFIG                 -> "Emma's awesome demo",
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG        -> "earliest",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG   -> "org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.ByteArrayDeserializer"
    ).asInstanceOf[Map[String, AnyRef]].asJava
}
