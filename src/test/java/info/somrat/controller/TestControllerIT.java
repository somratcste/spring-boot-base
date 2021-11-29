package info.somrat.controller;

import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.request.SignUpRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.response.JwtResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Primary
public class TestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/test";
    String token;

    public void createTestUser() {
        SignUpRequest user = new SignUpRequest("nazmul", "nazmul@gmail.com", Set.of("user"), "123456", "ACCESS_TEST2");
        HttpEntity<SignUpRequest> request = new HttpEntity<>(user);
        ResponseEntity<ApiResponse> result = restTemplate.postForEntity( "/api/auth/signup", request, ApiResponse.class);

        LoginRequest loginUser = new LoginRequest("nazmul", "123456");
        HttpEntity<LoginRequest> loginRequest = new HttpEntity<>(loginUser);
        ResponseEntity<JwtResponse> loginResult = restTemplate.postForEntity("/api/auth/signin", loginRequest, JwtResponse.class);
        token =  loginResult.getBody().getToken();
    }

    @Test
    public void allSuccess() {
        createTestUser();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/all", HttpMethod.GET, request, String.class);
        assertEquals("Public Content.", response.getBody());
    }
}
