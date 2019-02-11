package task_12.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * steps before start:
 * 1. download : 2.1.0 release and un-tar it.
 * 2. Unzip : tar -xzf kafka_2.11-2.1.0.tgz
 * 3. Go to folder : cd kafka_2.11-2.1.0
 * 4. create ZooKeeper : bin/zookeeper-server-start.sh config/zookeeper.properties
 * 5. start server : bin/kafka-server-start.sh config/server.properties
 * 6. create new topic : bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
 * 7. start Consumer
 * 8. start Producer
 *
 * класс реализует логику чтения сообщений от kafka сервера
 *
 * @author Комовский Дмитрий
 * @version v1.0
 */
public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}
