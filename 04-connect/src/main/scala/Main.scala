import java.util
import java.util.Properties

import org.apache.kafka.connect.api.ConnectEmbedded
import org.apache.kafka.connect.runtime.{ConnectorConfig, WorkerConfig}
import org.apache.kafka.connect.runtime.distributed.DistributedConfig
import org.apache.kafka.connect.sink.SinkTask

import scala.collection.JavaConverters._

object Main {
  def main(args: Array[String]) {
    val connect: ConnectEmbedded = new ConnectEmbedded(workerProperties, sinkProperties)
    connect.start()
  }

  val workerConfig: util.Map[String, AnyRef] = Map(
    DistributedConfig.GROUP_ID_CONFIG                  -> "mysql-connect",
    WorkerConfig.BOOTSTRAP_SERVERS_CONFIG              -> "localhost:9092",
    WorkerConfig.KEY_CONVERTER_CLASS_CONFIG            -> "org.apache.kafka.connect.storage.StringConverter",
    WorkerConfig.VALUE_CONVERTER_CLASS_CONFIG          -> "com.qubole.streamx.ByteArrayConverter",

    DistributedConfig.OFFSET_STORAGE_TOPIC_CONFIG      -> "connect-offsets",
    DistributedConfig.CONFIG_TOPIC_CONFIG              -> "connect-configs",
    DistributedConfig.STATUS_STORAGE_TOPIC_CONFIG      -> "connect-status",
    WorkerConfig.INTERNAL_KEY_CONVERTER_CLASS_CONFIG   -> "org.apache.kafka.connect.storage.StringConverter",
    WorkerConfig.INTERNAL_VALUE_CONVERTER_CLASS_CONFIG -> "org.apache.kafka.connect.storage.StringConverter"
  ).asInstanceOf[Map[String, AnyRef]].asJava

  val sinkConfig: util.Map[String, AnyRef] = Map(
    ConnectorConfig.NAME_CONFIG            -> "s3-connector",
    ConnectorConfig.CONNECTOR_CLASS_CONFIG -> "S3Connector",
    ConnectorConfig.TASKS_MAX_CONFIG       -> "1",
    SinkTask.TOPICS_CONFIG                 -> "demo-kafka-image"
  ).asInstanceOf[Map[String, AnyRef]].asJava

  val workerProperties: Properties = {
    val props = new Properties()
    props.putAll(workerConfig)
    props
  }

  val sinkProperties: Properties = {
    val props = new Properties()
    props.putAll(sinkConfig)
    props
  }
}
