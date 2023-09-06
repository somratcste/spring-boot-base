package info.somrat.rest.controller;

import info.somrat.rest.dto.GithubUserDTO;
import info.somrat.rest.response.ObjectListResponse;
import info.somrat.rest.service.github.GithubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/github")
@CrossOrigin(origins = "*")
public class GithubController {
    private static final Logger logger = LoggerFactory.getLogger(GithubController.class);

    @Autowired
    private GithubService githubService;

    @GetMapping("/users")
    public CompletableFuture<ResponseEntity<ObjectListResponse>> getUsers() {
        CompletableFuture<List<GithubUserDTO>> userFutures = githubService.getGitHubUsers();
        return userFutures.thenApply(users -> ResponseEntity.ok(new ObjectListResponse(true, "Get user successfully!", Collections.singletonList(users))));
    }
}
