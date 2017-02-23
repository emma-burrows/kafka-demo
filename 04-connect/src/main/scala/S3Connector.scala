import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util

import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.common.utils.AppInfoParser
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.sink.{SinkConnector, SinkRecord, SinkTask}
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata

import scala.collection.JavaConverters._

class S3Connector extends SinkConnector {

  override def taskClass(): Class[_ <: Task] = classOf[S3Task]

  override def taskConfigs(maxTasks: Int): util.List[util.Map[String, String]] =
    (1 to maxTasks).map { c =>
      val config: util.Map[String, String] = new util.HashMap[String, String]()
      config.put(SinkTask.TOPICS_CONFIG, "demo-kafka-image")
      config
    }.toList.asJava

  override def stop(): Unit = println("Stopped Sink Connector")

  override def config(): ConfigDef =
    new ConfigDef().define(SinkTask.TOPICS_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Topic to read from")

  override def start(props: util.Map[String, String]): Unit = {
    println("Start sink connector")
  }

  override def version(): String = AppInfoParser.getVersion
}

class S3Task extends SinkTask {
  val writer = new S3Writer()

  override def stop(): Unit = println("Stop task")

  override def put(records: util.Collection[SinkRecord]): Unit = {
    records.asScala.foreach { sinkRecord =>
      println(s"Reading message ${sinkRecord.key}")

      val path = s"PATH/file${sinkRecord.key}.png"
      writer.writeToS3("BUCKET-NAME", path, sinkRecord.value.asInstanceOf[Array[Byte]])
    }
  }

  override def flush(offsets: util.Map[TopicPartition, OffsetAndMetadata]): Unit = {}

  override def start(props: util.Map[String, String]): Unit = println("Start task")

  override def version(): String = AppInfoParser.getVersion
}

class S3Writer(s3Client: AmazonS3Client = new AmazonS3Client()) {

  def writeToS3(bucketName: String, path: String, fileBytes: Array[Byte]) = {
    println(s"Writing file to $bucketName/$path")
    val in = new ByteArrayInputStream(fileBytes)
    val metadata = new ObjectMetadata()
    metadata.setContentLength(fileBytes.length)
    s3Client.putObject(bucketName, path, in, metadata)
  }
}
