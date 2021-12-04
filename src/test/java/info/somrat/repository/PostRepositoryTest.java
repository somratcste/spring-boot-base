package info.somrat.repository;

import info.somrat.rest.models.Post;
import info.somrat.rest.repository.PostRepository;
import info.somrat.rest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByTitle")
    public void findByTitle() {
        Post post = new Post();
        post.setTitle("Title for Post");
        post.setUser(userRepository.getOne(1L));
        postRepository.save(post);

        assertEquals(post.getTitle(), postRepository.findByTitle(post.getTitle()).getTitle());
        assertEquals(1, List.of(postRepository.findAll()).size());
    }
}
