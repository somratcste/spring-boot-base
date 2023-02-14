package info.somrat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.somrat.rest.controller.PostController;
import info.somrat.rest.models.Post;
import info.somrat.rest.models.User;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import info.somrat.rest.response.ApiResponse;
import info.somrat.rest.service.PostService;
import info.somrat.rest.service.PostServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;

@UnsecuredWebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostServiceImpl postService;

//    @MockBean
//    private PostServiceImpl postServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/posts";

    @Test
    @DisplayName("POST /api/posts")
    public void signUp_basic() throws Exception {

        PostCreateRequest post = new PostCreateRequest("Post Title", "Description for Title 1", 10);

        when(postService.create(post)).thenReturn(new Post(1, "Post Title", "Description",
                new User("hossain", "hossain@gmail.com") ));

        RequestBuilder request = MockMvcRequestBuilders
                .post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApiResponse(true, "Post Created successfully!"))))
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        assertEquals("Post Title", jsonObject.getJSONObject("object").getString("title"));
        verify(postService, times(1)).create(refEq(post));
    }

    @Test
    @DisplayName("PUT /api/posts/{id}")
    public void update_basic() throws Exception {
        PostUpdateRequest post = new PostUpdateRequest("Learn Spring Boot", "Spring Boot with Unit Testing");

        when(postService.update(1, post)).thenReturn(new Post(1, "Learn Spring Boot", "Spring Boot with Unit Testing",
                new User("hossain", "hossain@gmail.com")));

        RequestBuilder request = MockMvcRequestBuilders
                .put(BASE_URL + "/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        assertEquals("Learn Spring Boot", jsonObject.getJSONObject("object").getString("title"));
    }

    @Test
    @DisplayName("DELETE /api/posts/{id}")
    public void delete_basic() throws Exception {
        when(postService.delete(1)).thenReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .delete(BASE_URL + "/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApiResponse(true, "Post Deleted Successfully!"))))
                .andReturn();
    }

    @Test
    @DisplayName("GET /api/posts/{id}")
    public void getById_basic() throws Exception {
        when(postService.getById(1)).thenReturn(new Post(1, "Learn Spring Boot", "Spring Boot with Unit Testing",
                new User("hossain", "hossain@gmail.com")));

        RequestBuilder request = MockMvcRequestBuilders
                .get(BASE_URL + "/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        assertEquals("Learn Spring Boot", jsonObject.getJSONObject("object").getString("title"));
    }

    @Test
    @DisplayName("DELETE /api/posts")
    public void deleteAll_basic() throws Exception {
        when(postService.deleteAll()).thenReturn(true);

        RequestBuilder request = MockMvcRequestBuilders
                .delete(BASE_URL )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApiResponse(true, "Deleted All Posts!"))))
                .andReturn();
    }

    @Test
    @DisplayName("GET /api/posts")
    public void getAll_basic() throws Exception {
        Page<Post> postPage = mock(Page.class);
        when(postService.getAll(0,10,"id", "Learn Spring Boot")).thenReturn(postPage);

        RequestBuilder request = MockMvcRequestBuilders
                .get(BASE_URL )
                .param("title", "Learn Spring Boot")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }
}
