import java.util
import java.util.Properties

import io.confluent.connect.s3.S3SinkConnectorConfig
import io.confluent.connect.storage.StorageSinkConnectorConfig
import io.confluent.connect.storage.common.StorageCommonConfig
import org.apache.kafka.connect.api.ConnectEmbedded
import org.apache.kafka.connect.runtime
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
    WorkerConfig.VALUE_CONVERTER_CLASS_CONFIG          -> "org.apache.kafka.connect.storage.ByteArrayConverter",
//    WorkerConfig.VALUE_CONVERTER_CLASS_CONFIG          -> "com.qubole.streamx.ByteArrayConverter",
//    WorkerConfig.VALUE_CONVERTER_CLASS_CONFIG          -> "io.confluent.connect.s3.format.bytearray.ByteArrayFormat",

    DistributedConfig.OFFSET_STORAGE_TOPIC_CONFIG      -> "connect-offsets",
    DistributedConfig.CONFIG_TOPIC_CONFIG              -> "connect-configs",
    DistributedConfig.STATUS_STORAGE_TOPIC_CONFIG      -> "connect-status",
    WorkerConfig.INTERNAL_KEY_CONVERTER_CLASS_CONFIG   -> "org.apache.kafka.connect.storage.StringConverter",
    WorkerConfig.INTERNAL_VALUE_CONVERTER_CLASS_CONFIG -> "org.apache.kafka.connect.storage.StringConverter"

  ).asInstanceOf[Map[String, AnyRef]].asJava

  val sinkConfig: util.Map[String, AnyRef] = Map(
    ConnectorConfig.NAME_CONFIG            -> "confluent-s3-sink",
    ConnectorConfig.CONNECTOR_CLASS_CONFIG -> "io.confluent.connect.s3.S3SinkConnector",
    ConnectorConfig.TASKS_MAX_CONFIG       -> "1",
    StorageSinkConnectorConfig.FLUSH_SIZE_CONFIG -> 256,
    StorageSinkConnectorConfig.FORMAT_CLASS_CONFIG -> "io.confluent.connect.s3.format.bytearray.ByteArrayFormat",
    SinkTask.TOPICS_CONFIG                 -> "demo-kafka-image",
    S3SinkConnectorConfig.S3_BUCKET_CONFIG -> "personhub-sandbox-test-324315958165",
    StorageCommonConfig.STORAGE_CLASS_CONFIG -> "io.confluent.connect.s3.storage.S3Storage"
  ).asInstanceOf[Map[String, AnyRef]].asJava


  /*
  name=s3-sink
connector.class=io.confluent.connect.s3.S3SinkConnector
tasks.max=1
topics=s3_topic

s3.region=us-west-2
s3.bucket.name=confluent-kafka-connect-s3-testing
s3.part.size=5242880
flush.size=3

storage.class=io.confluent.connect.s3.storage.S3Storage
format.class=io.confluent.connect.s3.format.avro.AvroFormat
#format.class=io.confluent.connect.s3.format.json.JsonFormat
schema.generator.class=io.confluent.connect.storage.hive.schema.DefaultSchemaGenerator
partitioner.class=io.confluent.connect.storage.partitioner.DefaultPartitioner

schema.compatibility=NONE
   */
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
