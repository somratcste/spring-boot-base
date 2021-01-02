package info.somrat.rest.request;

import info.somrat.rest.validators.UniqueEmail;
import info.somrat.rest.validators.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    @UniqueUsername
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @UniqueEmail
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
