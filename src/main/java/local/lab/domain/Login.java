package local.lab.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Login {
    private String id;
    private String name;
    private String accessToken;
    private String refreshToken;
}
