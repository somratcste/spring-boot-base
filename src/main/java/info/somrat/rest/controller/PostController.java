package info.somrat.rest.controller;

import info.somrat.rest.models.Post;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
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
    public ResponseEntity<ObjectResponse> create(@Valid @RequestBody PostCreateRequest post) {
        Post _post = postService.create(post);
        return ResponseEntity.ok(new ObjectResponse(true, "Post Created successfully!", _post));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ObjectResponse> update(@PathVariable("id") long id,  @Valid @RequestBody PostUpdateRequest post) {
        Post _post = postService.update(id, post);
        return ResponseEntity.ok(new ObjectResponse(true, "Post Updated Successfully!", _post));
    }
}
