package posts;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.kafka.KafkaMessageConsumer;
import org.example.kafka.KafkaMessageProducer;
import org.example.model.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CreateUserBasedOnKafkaEventTest {

    @BeforeMethod
    public static void sendUsers() throws JsonProcessingException {
        User usr1 = new User("Nat", "nat2902@gmail.com", "female", "active");
        User usr2 = new User("Anna", "ann8612@gmail.com", "female", "active");
        KafkaMessageProducer.sendUsersMessages(List.of(usr1, usr2));
    }

    @Test
    public static void shouldUserDataComeFromKafkaAndUserBeCreated() {
        KafkaMessageConsumer consumer = new KafkaMessageConsumer("kafka:9092", "users");
        try {
            consumer.pollAndPrintUsers();
        } finally {
            consumer.close();
        }
    }
}
