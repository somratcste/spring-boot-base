package info.somrat.rest.controller;

import info.somrat.rest.models.Post;
import info.somrat.rest.request.PostRequest;
import info.somrat.rest.response.ObjectResponse;
import info.somrat.rest.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ObjectResponse> create(@Valid @RequestBody PostRequest post) {

        Post _post = postService.create(post);
        return ResponseEntity.ok(new ObjectResponse(true, "User registered successfully!", _post));
    }
}
