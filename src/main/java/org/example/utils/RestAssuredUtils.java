package org.example.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestAssuredUtils {
    public static JsonPath readResponseJsonPath(Response response){
        return response
                .then()
                .extract()
                .jsonPath();
    }

    public static Response readResponse(Response response){
        return response
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
