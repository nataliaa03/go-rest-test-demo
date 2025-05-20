package org.example.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.model.User;

import java.util.List;
import java.util.Properties;

public class KafkaMessageProducer {

    protected static KafkaProducer producer;

    public static void sendUsersMessages(List<User> usersList) throws JsonProcessingException {
        producer = setupKafkaProducer();
        for (User user : usersList) {
            sendMessage("users", "user", user);
        }
        producer.flush();
        closeProducer();
    }

    public static void closeProducer() {
        if (producer != null) {
            producer.close();
        }
    }

    private static KafkaProducer setupKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092"); //name of the service from docker-compose
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(props);
    }

    private static void sendMessage(String topic, String key, Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(object);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, userJson);

        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("Message sent successfully to topic " + metadata.topic());
            }
        });
    }
}
