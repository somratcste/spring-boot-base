package info.somrat.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/hello")
    public String getHello() {
        logger.info("hello");
        return "Hello";
    }

    @GetMapping("/test1")
    public String getTest1() {
        return "Test 1";
    }

    @GetMapping("/test2")
    public String getTest2() {
        return "Test 2";
    }

    @GetMapping("/manager")
    public String getMessageFromManager() {
        return "hello from Manager!";
    }

    @GetMapping("/admin")
    public String getMessageFromAdmin() {
        return "hello from Admin!";
    }


}
