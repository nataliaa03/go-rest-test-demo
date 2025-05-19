package users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.SoftAssertions;
import org.example.User;
import org.example.service.UsersService;
import org.testng.annotations.Test;

public class GetUsersTest {

    @Test
    public void shouldUsersHaveProperData() throws JsonProcessingException {
        KafkaUtils.sendUsersMessages();
        User firstUser = UsersService.getUsersList().get(0);

        int userId = firstUser.id;
        String userName = firstUser.name;
        String userEmail = firstUser.email;
        String userStatus = firstUser.status;
        String userGender = firstUser.gender;

        SoftAssertions assertions = new SoftAssertions();

        assertions.assertThat(userId).isInstanceOf(Integer.class);
        assertions.assertThat(userId).isGreaterThan(0);
        assertions.assertThat(userName).isNotBlank();
        assertions.assertThat(userEmail).contains("@");
        assertions.assertThat(userStatus).isIn("active", "inactive");
        assertions.assertThat(userGender).isIn("male", "female");

        assertions.assertAll();
    }


}
