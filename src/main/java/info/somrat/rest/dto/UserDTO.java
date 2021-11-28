package info.somrat.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDTO {

    private String username;
    private String email;
    private List<String> permissions;
    private List<String> roles;
}
