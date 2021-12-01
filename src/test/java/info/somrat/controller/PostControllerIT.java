package info.somrat.controller;

import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.response.JwtResponse;
import info.somrat.rest.response.ObjectResponse;
import info.somrat.rest.response.PageResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final String BASE_URL = "/api/posts";
    HttpHeaders headers;

    @BeforeAll
    public void getUserRequest() {
        LoginRequest user = new LoginRequest("hossain", "123456");
        HttpEntity<LoginRequest> loginRequest = new HttpEntity<>(user);
        ResponseEntity<JwtResponse> result = restTemplate.postForEntity("/api/auth/signin", loginRequest, JwtResponse.class);
        headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + result.getBody().getToken());
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @Order(1)
    public void createPostSuccess() {
        PostCreateRequest post = new PostCreateRequest("Title 1", "Description for Title 1", 1);
        HttpEntity<PostCreateRequest> request = new HttpEntity<>(post, headers);
        ResponseEntity<ObjectResponse> result = restTemplate.postForEntity(BASE_URL , request, ObjectResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void getByIdSuccess() {
        HttpEntity<PostUpdateRequest> request = new HttpEntity<>(headers);
        ResponseEntity<ObjectResponse> result = restTemplate.exchange(BASE_URL + "/1" , HttpMethod.GET, request, ObjectResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    /**
     * Send query params with request
     * When we use UriComponent, we have to use actual url
     */
    @Test
    @Order(2)
    public void getAllSuccess() {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + BASE_URL)
                .queryParam("title", "Title")
                .toUriString();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<PageResponse> result = restTemplate.exchange(urlTemplate , HttpMethod.GET, request, PageResponse.class);
        assertEquals(1, result.getBody().getTotalItems());
    }

    /**
     * First we have to create a post
     * Otherwise the url id 1 will be empty and
     * Test failed
     */
    @Test
    @Order(2)
    public void updatePostSuccess() {
        PostUpdateRequest post = new PostUpdateRequest("Title 1 Update", "Description Updated");
        HttpEntity<PostUpdateRequest> request = new HttpEntity<>(post, headers);
        ResponseEntity<ObjectResponse> result = restTemplate.exchange(BASE_URL + "/1" , HttpMethod.PUT, request, ObjectResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void deletePostSuccess() {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ObjectResponse> result = restTemplate.exchange(BASE_URL + "/1" , HttpMethod.DELETE, request, ObjectResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void deleteAllSuccess() {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> result = restTemplate.exchange(BASE_URL , HttpMethod.DELETE, request, ApiResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }
}
