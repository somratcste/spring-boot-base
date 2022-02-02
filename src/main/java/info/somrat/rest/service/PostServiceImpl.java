package info.somrat.rest.service;

import info.somrat.rest.models.Post;
import info.somrat.rest.models.User;
import info.somrat.rest.repository.PostRepository;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import info.somrat.rest.utils.Helper;
import info.somrat.rest.validators.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService, FieldValueExists {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
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

    public Post update(int id, PostUpdateRequest post) {
        Optional<Post> postData = postRepository.findById((long) id);
        if (postData.isPresent()) {
            Post _post = postData.get();
            _post.setTitle(post.getTitle());
            _post.setDescription(post.getDescription());
            return postRepository.save(_post);
        } else {
            throw new EntityNotFoundException("Post not found!");
        }
    }

    public boolean delete(long id) {
        Optional<Post> _post = postRepository.findById(id);
        if (_post.isPresent()) {
            postRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Post getById(long id) {
        Optional<Post> _post = postRepository.findById(id);
        if (_post.isPresent()) {
            return _post.get();
        } else {
            throw new EntityNotFoundException("Post not found!");
        }
    }

    public boolean deleteAll() {
        try {
            postRepository.deleteAll();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Page<Post> getAll(int pageNo, int pageSize, String sortBy, String title) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        Page<Post> postPage;
        if (!Helper.isNullOrEmpty(title)) {
            postPage = postRepository.findByTitleContaining(title, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }
        return postPage;
    }

    public Page getAllPostsByUser(int pageNo, int pageSize, long userId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> postPage = postRepository.findByUserIdOrderByIdDesc(userId, pageable);
        return postPage;
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
