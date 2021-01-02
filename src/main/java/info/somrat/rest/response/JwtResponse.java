package info.somrat.rest.response;

import info.somrat.rest.jwt.JwtProperties;
import info.somrat.rest.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String tokenType = JwtProperties.TOKEN_PREFIX.replace(" ", "");
    private User user;

    public JwtResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
