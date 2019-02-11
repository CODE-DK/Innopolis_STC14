package task_12.produsers;

import java.util.Properties;
import org.apache.kafka.clients.producer.*;

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
 * класс реализует логику клиента сообщений для kafka сервера
 *
 * @author Комовский Дмитрий
 * @version v1.0
 */
public class Producer {
    public static void main(String[] args){

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("delivery.timeout.ms", 30002);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
        producer.close();
    }
}
