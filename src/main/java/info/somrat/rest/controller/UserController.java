package info.somrat.rest.controller;

import info.somrat.rest.models.Post;
import info.somrat.rest.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private PostService postService;


    @GetMapping("/{id}/posts")
    public ResponseEntity<?> getAllPostByUser(@PathVariable("id") long userId,
                                                       @RequestParam(defaultValue = "0") int pageNo,
                                                       @RequestParam(defaultValue = "10") int pageSize
                                         ) {
        Page<Post> postPage= postService.getAllPostsByUser(pageNo, pageSize, userId);
        return ResponseEntity.ok(postPage.getContent());
    }
}
