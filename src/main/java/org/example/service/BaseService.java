package org.example.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class BaseService {
    protected static final String baseUrl = "https://gorest.co.in/public/v2";

    public static RequestSpecification given() {
        return RestAssured.given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public static String getToken() {
        return System.getenv("GOREST_TOKEN");
    }
}
