package info.somrat.controller;

import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.response.JwtResponse;
import info.somrat.rest.response.ObjectResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/posts";
    HttpHeaders headers;

    @BeforeAll
    public void getUserRequest() {
        LoginRequest user = new LoginRequest("hossain", "123456");
        HttpEntity<LoginRequest> loginRequest = new HttpEntity<>(user);
        ResponseEntity<JwtResponse> result = restTemplate.postForEntity("/api/auth/signin", loginRequest, JwtResponse.class);
        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + result.getBody().getToken());
    }

    @Test
    public void createPostSuccess() throws NoSuchFieldException {
        PostCreateRequest post = new PostCreateRequest("Title 1", "Description for Title 1", 1);
        HttpEntity<PostCreateRequest> request = new HttpEntity<>(post, headers);
        ResponseEntity<ObjectResponse> result = restTemplate.postForEntity(BASE_URL , request, ObjectResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }
}
