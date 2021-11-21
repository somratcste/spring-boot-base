package info.somrat.rest.request;

import info.somrat.rest.service.PostService;
import info.somrat.rest.validators.Unique;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class PostUpdateRequest {

    @NotBlank
    @Unique(service = PostService.class, fieldName = "title", message = "The post title must be unique!")
    private String title;

    @NotBlank
    private String description;
}
