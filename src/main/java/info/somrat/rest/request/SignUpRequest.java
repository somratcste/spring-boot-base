package info.somrat.rest.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Name is mandatory")
    private String username;
    private String password;
}
