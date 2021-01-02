package info.somrat.rest.controller;

import info.somrat.rest.jwt.JwtTokenProvider;
import info.somrat.rest.models.User;
import info.somrat.rest.request.LoginRequest;
import info.somrat.rest.request.SignUpRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.response.JwtResponse;
import info.somrat.rest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * User registration
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.save(request);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully!"));
    }

    /**
     * Authenticate a user
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
        User user = userService.findByUsername(request.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwtToken, user));
    }

    @GetMapping("/hello")
    public String getHello() {
        logger.info("hello");
        return "Hello";
    }

}
