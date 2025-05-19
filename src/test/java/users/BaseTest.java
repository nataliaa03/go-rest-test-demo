import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import java.util.Properties;

public class BaseTest {

    private static KafkaProducer<String, String> producer;

    @BeforeSuite
    public void setupProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092"); //name of the service from docker-compose
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create the producer
        producer = new KafkaProducer<>(props);

        // Create a producer record
        ProducerRecord<String, String> record = new ProducerRecord<>("First test Messages", "key", "Hello, Kafka!");
        producer.send(record);
    }


    @AfterSuite
    public void closeProducer() {
        if (producer != null) {
            producer.close();
        }
    }
}
