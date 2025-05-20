package posts;

import groovy.util.logging.Slf4j;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.example.model.Post;
import org.example.model.User;
import org.example.service.PostsService;
import org.example.service.UsersService;
import org.example.utils.RestAssuredUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class CRUDPostTest {
    private static final String POST_TITLE_1 = "First Test Post 123 - First";
    private static final String POST_TITLE_2 = "First Test Post 123 - Second";
    private static final String POST_BODY = "Body Of the first Post 123$%^& :)";
    private static int userId;

    @BeforeMethod
    public static void cleanUpBefore() {
        cleanUpPosts();
    }


    @Test
    public static void shouldPostBeAddedCorrectly() {
        User firstUser = UsersService.getUsersList().get(0);

        userId = firstUser.id;

        //CREATE POST
        Response createPostResp = PostsService.createUserPost(userId, POST_TITLE_1, POST_BODY);
        JsonPath jPath = RestAssuredUtils.readResponseJsonPath(createPostResp);

        SoftAssertions assertions1 = new SoftAssertions();
        assertions1.assertThat(RestAssuredUtils.readResponse(createPostResp).statusCode()).isEqualTo(201);
        assertions1.assertThat(jPath.getInt("id")).isGreaterThan(0);
        assertions1.assertThat(jPath.getInt("user_id")).isEqualTo(userId);
        assertions1.assertThat(jPath.getString("title")).isEqualTo(POST_TITLE_1);
        assertions1.assertThat(jPath.getString("body")).isEqualTo(POST_BODY);
        assertions1.assertAll();

        //CREATE SECOND POST
        PostsService.createUserPost(userId, POST_TITLE_2, POST_BODY);

        //GET ALL USER POSTS
        Response postsResp = PostsService.getUserPostsByUserId(userId);
        JsonPath postJsonPath = postsResp.jsonPath();

        List<Post> postsList = postJsonPath.getList("$", Post.class);
        int post1_id = postsList.get(0).id;
        int post2_id = postsList.get(1).id;

        Response getUserPostsResp = PostsService.getUserPostsByUserId(userId);

        SoftAssertions assertions2 = new SoftAssertions();
        assertions2.assertThat(getUserPostsResp.statusCode()).isEqualTo(200);

        assertions2.assertThat(postsResp.statusCode()).isEqualTo(200);
        assertions2.assertThat(postsList.size()).isEqualTo(2);
        assertions2.assertThat(postsList.get(0).title).isEqualTo(POST_TITLE_2);
        assertions2.assertThat(postsList.get(0).body).isEqualTo(POST_BODY);
        assertions2.assertThat(postsList.get(0).user_id).isEqualTo(userId);
        assertions2.assertThat(postsList.get(1).title).isEqualTo(POST_TITLE_1);
        assertions2.assertThat(postsList.get(1).body).isEqualTo(POST_BODY);
        assertions2.assertThat(postsList.get(1).user_id).isEqualTo(userId);
        assertions2.assertThat(post1_id).isGreaterThan(0);
        assertions2.assertThat(post2_id).isGreaterThan(0);
        assertions2.assertAll();

        //DELETE POSTS
        Response delResp1 = PostsService.deletePostByPostId(post1_id);
        assertThat(delResp1.statusCode()).isEqualTo(204);

        Response delResp2 = PostsService.deletePostByPostId(post2_id);
        assertThat(delResp2.statusCode()).isEqualTo(204);

        //GET USER POSTS
        Response getUserPostsResp2 = PostsService.getUserPostsByUserId(userId);
        var postsList2size = getUserPostsResp2.jsonPath().getList("$", Post.class).size();

        assertThat(postsList2size).isEqualTo(0);
    }


    @AfterMethod
    public static void cleanUpAfter() {
        cleanUpPosts();
    }



    private static void cleanUpPosts() {
        List<Post> userPosts = RestAssuredUtils.readResponseJsonPath(PostsService.getUserPostsByUserId(userId)).getList("$", Post.class);
        if (userPosts.size() > 0) {
            System.out.println("Cleaned " + userPosts.size() + " posts.");
            for (Post p : userPosts) {
                PostsService.deletePostByPostId(p.id);
            }
        } else {
            System.out.println("User " + userId + " has no posts.");
        }

    }
}

