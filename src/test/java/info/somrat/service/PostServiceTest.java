package info.somrat.service;

import info.somrat.rest.models.Post;
import info.somrat.rest.models.User;
import info.somrat.rest.repository.PostRepository;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import info.somrat.rest.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Create Post")
    public void create() {
        PostCreateRequest postCreateRequest = new PostCreateRequest("Unit Test for Service Layer", "Description", 1);
        User user = new User("hossain", "hossain@gmail.com");

        Post _post = new Post();
        _post.setTitle(postCreateRequest.getTitle());
        _post.setDescription(postCreateRequest.getDescription());
        _post.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(_post)).thenReturn(_post);

        Post createdPost = postService.create(postCreateRequest);
        assertEquals("Unit Test for Service Layer", createdPost.getTitle());
    }

    @Test
    @DisplayName("Update Post")
    public void update() {
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("Unit Test for Service Layer", "Description");
        Post post = new Post();
        post.setTitle(postUpdateRequest.getTitle());

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(post);

        Post updatedPost = postService.update(1, postUpdateRequest);
        assertEquals("Unit Test for Service Layer", updatedPost.getTitle());
    }

    @Test
    @DisplayName("Delete Post")
    public void delete() {
        Post post = new Post();
        post.setId(1);
        post.setTitle("Unit Test for Service Layer");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.delete(post.getId());
        verify(postRepository).deleteById(post.getId());

    }

    @Test
    @DisplayName("Get By Id")
    public void getById() {
        Post post = new Post();
        post.setTitle("Unit Test for Service Layer");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post dbPost = postService.getById(1L);
        assertEquals("Unit Test for Service Layer", dbPost.getTitle());
    }

    @Test
    @DisplayName("Delete All")
    public void deleteAll() {
        postService.deleteAll();
        verify(postRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Get All")
    public void getAll() {
//         example to create a page list
        Post firstPost = new Post();
        firstPost.setTitle("First Post");
        Post secondPost = new Post();
        secondPost.setTitle("Second Post");

        Page<Post> pageWithOutTitle = new PageImpl<Post>(Arrays.asList(firstPost, secondPost));
        Page<Post> pageWithTitle = new PageImpl<Post>(Arrays.asList(firstPost));

        given(postRepository.findAll(any(Pageable.class))).willReturn(pageWithOutTitle);
        given(postRepository.findByTitleContaining(anyString(), any(Pageable.class))).willReturn(pageWithTitle);

        Page<Post> resultWithOutTitle = postService.getAll(0, 10, "id", null);
        assertEquals(2, resultWithOutTitle.getTotalElements());

        Page<Post> resultWithTitle = postService.getAll(0, 10, "id", "First Post");
        assertEquals(pageWithTitle, resultWithTitle);
        assertEquals(1, resultWithTitle.getTotalElements());
        assertEquals(1, resultWithTitle.getTotalPages());
        assertThat(resultWithTitle.getContent()).hasSize(1);
    }

}
