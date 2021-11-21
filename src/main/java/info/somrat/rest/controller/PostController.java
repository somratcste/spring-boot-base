package info.somrat.rest.controller;

import info.somrat.rest.models.Post;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.response.ObjectResponse;
import info.somrat.rest.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") long id) {
        boolean deletePost = postService.delete(id);
        if (deletePost) {
            return ResponseEntity.ok(new ApiResponse(true, "Post Deleted Successfully!"));
        } else {
            return new ResponseEntity(new ApiResponse(false, "Post Not Found!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ObjectResponse> getById(@PathVariable("id") long id) {
        Post _post = postService.getById(id);
        return ResponseEntity.ok(new ObjectResponse(true, "", _post));
    }

    @DeleteMapping("/posts")
    public ResponseEntity<ApiResponse> deleteAll() {
        boolean deleteAll = postService.deleteAll();
        if (deleteAll) {
            return ResponseEntity.ok(new ApiResponse(true, "Deleted All Posts!"));
        } else {
            return new ResponseEntity(new ApiResponse(false, "Something goes wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
