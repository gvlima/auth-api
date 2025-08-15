package local.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(@JsonProperty("access_token") String accessToken, @JsonProperty("refresh_token") String refreshToken) {
}
