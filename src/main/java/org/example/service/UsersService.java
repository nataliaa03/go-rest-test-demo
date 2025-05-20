package org.example.service;

import io.restassured.response.Response;
import org.example.model.User;

import java.util.HashMap;
import java.util.List;

public class UsersService extends BaseService {
    public static final String url = baseUrl + "/users";


    public static Response getUsers() {
        return given()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static List<User> getUsersList() {
        return getUsers()
                .getBody()
                .jsonPath()
                .getList("$", User.class);
    }

    public static Response createUser(String name, String gender, String email, String status) {
        HashMap<String, String> userMap = createUserMap(name, gender, email, status);

        return given()
                .body(userMap)
                .post(url);

    }

    public static Response updateUser(int id, String name, String gender, String email, String status) {
        HashMap<String, String> userMap = createUserMap(name, gender, email, status);

        return given()
                .body(userMap)
                .put(url + "/" + id);
    }

    public static Response deleteUser(int id) {
        return given()
                .delete(url + "/" + id)
                .then()
                .statusCode(204)
                .extract()
                .response();
    }

    public static Response getUserById(int id) {
        return given().get(url + "/" + id);
    }

    private static String generateRandomEmail(String name) {
        return name.toLowerCase().replace(" ", "") + (int) (Math.random() * 1000000) + "@test.com";
    }

    private static HashMap<String, String> createUserMap(String name, String gender, String email, String status) {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("email", email == null ? generateRandomEmail(name) : email);
        userMap.put("gender", gender);
        userMap.put("status", status);
        return userMap;
    }
}
