package org.example.service;

import io.restassured.response.Response;

import java.util.HashMap;

public class PostsService extends BaseService {
    public static final String url = baseUrl + "/users/posts";

    public static Response createUserPost(int userId, String title, String body) {
        HashMap<String, String> postMap = createPostMap(title, body);
        return given()
                .body(postMap)
                .post(url.replace("/posts", "/" + userId + "/posts"));
    }

    public static Response getUserPostsByUserId(int userId){
    return given()
            .get(url.replace("/posts", "/" + userId + "/posts"));
    }

    public static Response deletePostByPostId(int postId){
     return given()
             .delete(url.replace("/users/posts", "/posts/" + postId));
    }

    public static HashMap<String, String> createPostMap(String title,  String body) {
        HashMap<String, String> postMap = new HashMap<>();
        postMap.put("title", title);
        postMap.put("body", body);

        return postMap;
    }
}
