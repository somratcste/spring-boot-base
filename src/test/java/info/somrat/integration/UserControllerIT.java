package info.somrat.integration;

import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.response.JwtResponse;
import info.somrat.rest.response.PageResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final String BASE_URL = "/api/users";
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

    /**
     * Get user id from dummy data
     */
    @Test
    public void getAllPostByUserSuccess() {
        Map<String, String> uriVariables = new HashMap<>();
        // assign user id
        uriVariables.put("id", "1");
        final String url = "http://localhost:" + port + BASE_URL + "/{id}/posts";
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<PageResponse> result = restTemplate.exchange( url, HttpMethod.GET, request, PageResponse.class, uriVariables);
        assertEquals(0, result.getBody().getTotalItems());
    }

}
