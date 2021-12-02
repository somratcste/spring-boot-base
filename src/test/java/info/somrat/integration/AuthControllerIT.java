package info.somrat.integration;

import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.request.SignUpRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.response.JwtResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/auth";

    @Test
    @Order(1)
    public void signUpSuccess() {
        SignUpRequest user = new SignUpRequest("nazmul", "nazmul@gmail.com", Set.of("user"),
                "123456", "ACCESS_TEST");

        HttpEntity<SignUpRequest> request = new HttpEntity<>(user);
        ResponseEntity<ApiResponse> result = restTemplate.postForEntity(BASE_URL + "/signup", request, ApiResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    public void singInSuccess() {
        LoginRequest user = new LoginRequest("hossain", "123456");

        HttpEntity<LoginRequest> request = new HttpEntity<>(user);
        ResponseEntity<JwtResponse> result = restTemplate.postForEntity(BASE_URL + "/signin", request, JwtResponse.class);
        assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
    }

    @Test
    public void getHelloSuccess() {
        String response = restTemplate.getForObject(BASE_URL + "/hello", String.class);
        assertEquals("Hello", response);
    }

}
