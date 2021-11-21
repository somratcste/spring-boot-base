package info.somrat.rest.service;

import info.somrat.rest.models.Post;
import info.somrat.rest.models.User;
import info.somrat.rest.repository.PostRepository;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import info.somrat.rest.validators.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PostService implements FieldValueExists {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post create(PostCreateRequest post) {
        Post _post = new Post();
        _post.setTitle(post.getTitle());
        _post.setDescription(post.getDescription());
        Optional<User> user = userRepository.findById((long) post.getUserId());
        if (user.isPresent()) {
            _post.setUser(user.get());
        } else {
            throw new EntityNotFoundException("User not found!");
        }
        return postRepository.save(_post);
    }

    public Post update(long id, PostUpdateRequest post) {
        Optional<Post> postData = postRepository.findById(id);
        if (postData.isPresent()) {
            Post _post = postData.get();
            _post.setTitle(post.getTitle());
            _post.setDescription(post.getDescription());
            return postRepository.save(_post);
        } else {
            throw new EntityNotFoundException("Post not found!");
        }
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("title")) {
            throw new UnsupportedOperationException("Field name not supported");
        }
        if (value == null) {
            return false;
        }
        return postRepository.findByTitle(value.toString()) == null ? false : true;
    }
}
