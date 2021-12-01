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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/test";
    HttpEntity<String> request;

    @BeforeAll
    public void getUserRequest() {
        LoginRequest user = new LoginRequest("hossain", "123456");
        HttpEntity<LoginRequest> loginRequest = new HttpEntity<>(user);
        ResponseEntity<JwtResponse> result = restTemplate.postForEntity("/api/auth/signin", loginRequest, JwtResponse.class);
        String token =  result.getBody().getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        request = new HttpEntity<>(headers);
    }

    @Test
    public void allSuccess() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/all", HttpMethod.GET, request, String.class);
        assertEquals("Public Content.", response.getBody());
    }

    @Test
    public void userAccessSuccess() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/user", HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void ModeratorAccessSuccess() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/moderator", HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void adminAccessSuccess() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/admin", HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
