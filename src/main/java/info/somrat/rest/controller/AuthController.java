package info.somrat.rest.controller;

import info.somrat.rest.jwt.JwtTokenProvider;
import info.somrat.rest.repository.RoleRepository;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.SignUpRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RoleRepository roleRepository;

    /**
     * User registration
     * @param request
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.save(request);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully!"));
    }

    @GetMapping("/hello")
    public String getHello() {
        logger.info("hello");
        return "Hello";
    }

}
