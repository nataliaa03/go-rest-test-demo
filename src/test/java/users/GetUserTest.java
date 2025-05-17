package users;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.example.service.UsersService;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetUserTest {
    @Test
    public void shouldReturnNoUserWhenInvalidId(){
        int lastUserId = UsersService.getUsersList().get(0).id;
        Response userGetResponse = UsersService.getUserById(lastUserId+1);

        String message = userGetResponse.jsonPath().getString("message");
        int status = userGetResponse.statusCode();

        assertThat(message).isEqualTo("Resource not found");
        assertThat(status).isEqualTo(404);
    }
}
