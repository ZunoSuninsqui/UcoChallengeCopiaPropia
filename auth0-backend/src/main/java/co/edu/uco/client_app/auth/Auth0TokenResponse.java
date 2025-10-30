package co.edu.uco.client_app.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Auth0TokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("refresh_token") String refreshToken,
        String scope,
        @JsonProperty("expires_in") Integer expiresIn,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("issued_token_type") String issuedTokenType
) {
}
