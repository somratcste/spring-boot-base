package info.somrat.rest.response;

import info.somrat.rest.dto.UserDTO;
import info.somrat.rest.jwt.JwtProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String tokenType = JwtProperties.TOKEN_PREFIX.replace(" ", "");
    private UserDTO user;

    public JwtResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
}
