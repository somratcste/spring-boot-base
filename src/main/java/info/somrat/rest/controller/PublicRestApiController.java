package info.somrat.rest.controller;

import info.somrat.rest.models.User;
import info.somrat.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("test1")
    public String test1(){
        return "API Test 1";
    }

    @GetMapping("test2")
    @PreAuthorize("hasAuthority('ACCESS_TEST2')")
    public String test2(){
        return "API Test 2";
    }

    @GetMapping("test3")
    public String test3(){
        return "API Test 3";
    }

    @GetMapping("users")
    public List<User> users(){
        return this.userRepository.findAll();
    }

}