package info.somrat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.somrat.rest.controller.PostController;
import info.somrat.rest.models.Post;
import info.somrat.rest.models.User;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@UnsecuredWebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/posts";

    @Test
    @DisplayName("Create Post /api/posts")
    public void signUp_basic() throws Exception {

        PostCreateRequest post = new PostCreateRequest("Title 1", "Description for Title 1", 10);

        when(postService.create(post)).thenReturn(new Post(1, "Title 1", "Description",
                new User("hossain", "hossain@gmail.com") ));

        RequestBuilder request = MockMvcRequestBuilders
                .post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true,\"message\":\"Post Created successfully!\"}"))
                .andReturn();

        verify(postService, times(1)).create(refEq(post));
    }

}
