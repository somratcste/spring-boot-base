package info.somrat.controller;

import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.response.JwtResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/test";
    String token;

    public void getUserToken() {
        LoginRequest user = new LoginRequest("hossain", "123456");
        HttpEntity<LoginRequest> loginRequest = new HttpEntity<>(user);
        ResponseEntity<JwtResponse> result = restTemplate.postForEntity("/api/auth/signin", loginRequest, JwtResponse.class);
        token =  result.getBody().getToken();
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    public void allSuccess() {
        getUserToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/all", HttpMethod.GET, request, String.class);
        assertEquals("Public Content.", response.getBody());
    }
}
