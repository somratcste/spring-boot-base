package info.somrat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.somrat.rest.controller.AuthController;
import info.somrat.rest.jwt.JwtTokenProvider;
import info.somrat.rest.models.User;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.SignUpRequest;
import info.somrat.rest.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Set;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@UnsecuredWebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/auth";

    @Test
    @DisplayName("POST /api/auth/signup")
    public void signUp_basic() throws Exception {

        SignUpRequest user = new SignUpRequest("hossain", "hossain@gmail.com", Set.of("user"),
                "123456", "ACCESS_TEST1");
        when(userService.save(user)).thenReturn(new User("hossain", "hossain@gmail.com"));

        RequestBuilder request = MockMvcRequestBuilders
                .post(BASE_URL + "/signup")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"message\":\"User registered successfully!\"}"))
                .andReturn();

        verify(userService, times(1)).save(refEq(user));
    }
}
