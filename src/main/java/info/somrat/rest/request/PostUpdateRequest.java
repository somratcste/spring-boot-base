package info.somrat.rest.request;

import info.somrat.rest.service.PostService;
import info.somrat.rest.service.PostServiceImpl;
import info.somrat.rest.validators.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor
public class PostUpdateRequest {

    @NotBlank
    @Unique(service = PostServiceImpl.class, fieldName = "title", message = "The post title must be unique!")
    private String title;

    @NotBlank
    private String description;
}
