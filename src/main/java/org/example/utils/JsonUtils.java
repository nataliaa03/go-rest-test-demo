package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import io.restassured.response.Response;

@Slf4j
public class JsonUtils {
    public static JsonNode readResponseJson(Response response) {
        var mapper = new ObjectMapper();
        try {
            return mapper.readTree(response.then().extract().body().asString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
