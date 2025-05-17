package users;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.example.service.UsersService;
import org.example.utils.RestAssuredUtils;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CRUDUserTest {
    private static int createdUserId;
    private static String createdUserEmail;

    @Test
    public void shouldUserBeCreatedUpdatedDeleted() {
        //CREATE
        Response userCreateResp = UsersService.createUser("User Test", "female", null, "active");
        var jsonPath = RestAssuredUtils.readResponseJsonPath(userCreateResp);
        createdUserId = jsonPath.getInt("id");
        createdUserEmail = jsonPath.getString("email");
        JsonPath userGetResp = UsersService.getUserById(createdUserId).jsonPath();

        var userGetIdActual = userGetResp.getInt("id");
        var userGetNameActual = userGetResp.getString("name");
        var userGetEmailActual = userGetResp.getString("email");
        var userGetGenderActual = userGetResp.getString("gender");
        var userGetStatusActual = userGetResp.getString("status");

        SoftAssertions assertions1 = new SoftAssertions();
        assertions1.assertThat(userGetIdActual).isEqualTo(createdUserId);
        assertions1.assertThat(userGetNameActual).isEqualTo("User Test");
        assertions1.assertThat(userGetEmailActual).isEqualTo(createdUserEmail);
        assertions1.assertThat(userGetGenderActual).isEqualTo("female");
        assertions1.assertThat(userGetStatusActual).isEqualTo("active");
        assertions1.assertAll();

        //UPDATE
        String emailUpdated = createdUserEmail.replace("usertest", "userupdated");
        UsersService.updateUser(createdUserId, "User Updated", "male", emailUpdated, "inactive");
        JsonPath updatedUserGetResp = UsersService.getUserById(createdUserId).jsonPath();

        var userGetIdUpdatedActual = updatedUserGetResp.getInt("id");
        var userGetNameUpdatedActual = updatedUserGetResp.getString("name");
        var userGetEmailUpdatedActual = updatedUserGetResp.getString("email");
        var userGetGenderUpdatedActual = updatedUserGetResp.getString("gender");
        var userGetStatusUpdatedActual = updatedUserGetResp.getString("status");

        SoftAssertions assertions2 = new SoftAssertions();
        assertions2.assertThat(userGetIdUpdatedActual).isEqualTo(createdUserId);
        assertions2.assertThat(userGetNameUpdatedActual).isEqualTo("User Updated");
        assertions2.assertThat(userGetEmailUpdatedActual).isEqualTo(emailUpdated);
        assertions2.assertThat(userGetGenderUpdatedActual).isEqualTo("male");
        assertions2.assertThat(userGetStatusUpdatedActual).isEqualTo("inactive");
        assertions2.assertAll();

        //DELETE
        UsersService.deleteUser(createdUserId);
        String userGetRespMessage = UsersService.getUserById(createdUserId).jsonPath().getString("message");

        assertThat(userGetRespMessage).isEqualTo("Resource not found");

    }
}
