package info.somrat.controller;

import info.somrat.rest.controller.UserController;
import info.somrat.rest.models.Post;
import info.somrat.rest.service.PostService;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@UnsecuredWebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private final String BASE_URL = "/api/users";

    @Test
    @DisplayName("GET /api/users/{id}/posts")
    public void getAllPostByUser_Basic() throws Exception {
        Page<Post> postPage = mock(Page.class);
        when(postService.getAllPostsByUser(0,10,1)).thenReturn(postPage);

        RequestBuilder request = MockMvcRequestBuilders
                .get(BASE_URL + "/{id}/posts", 1 )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

}
