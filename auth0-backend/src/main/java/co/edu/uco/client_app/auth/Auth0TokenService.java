package co.edu.uco.client_app.auth;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Auth0TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Auth0TokenService.class);

    private final RestClient restClient;
    private final Auth0Properties properties;

    public Auth0TokenService(RestClient.Builder restClientBuilder, Auth0Properties properties) {
        this.restClient = restClientBuilder.build();
        this.properties = properties;
    }

    public Auth0TokenResponse exchangeAuthorizationCode(String code, String codeVerifier, String requestedRedirectUri) {
        Assert.hasText(code, "authorization code must not be blank");
        Assert.hasText(properties.getClient().getId(), "auth0.client.id must be configured");

        URI tokenUri = properties.tokenEndpointUri();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("code", code);
        form.add("client_id", properties.getClient().getId());

        if (StringUtils.hasText(properties.getClient().getSecret())) {
            form.add("client_secret", properties.getClient().getSecret());
        }

        String redirectUri = resolveRedirectUri(requestedRedirectUri);
        if (StringUtils.hasText(redirectUri)) {
            form.add("redirect_uri", redirectUri);
        }

        if (StringUtils.hasText(codeVerifier)) {
            form.add("code_verifier", codeVerifier);
        }

        if (StringUtils.hasText(properties.getAudience())) {
            form.add("audience", properties.getAudience());
        }

        try {
            LOGGER.info("[Auth0] Intercambiando code por tokens en {}", tokenUri);
            return restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .body(Auth0TokenResponse.class);
        } catch (RestClientResponseException ex) {
            String details = ex.getResponseBodyAsString();
            LOGGER.error("[Auth0] Error {} {} al intercambiar token", ex.getStatusCode(), details);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Auth0 rechazó el intercambio de token: " + summarize(details), ex);
        } catch (RestClientException ex) {
            LOGGER.error("[Auth0] Error de red al comunicarse con Auth0", ex);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No se pudo contactar el token endpoint de Auth0", ex);
        }
    }

    private String resolveRedirectUri(String requestedRedirectUri) {
        if (StringUtils.hasText(requestedRedirectUri)) {
            return requestedRedirectUri;
        }
        return properties.getClient().getRedirectUri();
    }

    private String summarize(String body) {
        if (!StringUtils.hasText(body)) {
            return "sin detalles";
        }
        return body.length() > 256 ? body.substring(0, 253) + "..." : body;
    }
}
