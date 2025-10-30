package co.edu.uco.client_app.auth;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "auth0")
public class Auth0Properties {

    private String issuerUri;
    private String audience;
    private String rolesClaim;
    private String permissionsClaim = "permissions";
    private String tokenEndpoint;
    private final Client client = new Client();

    public URI tokenEndpointUri() {
        if (StringUtils.hasText(tokenEndpoint)) {
            return URI.create(tokenEndpoint);
        }
        Assert.hasText(issuerUri, "auth0.issuer-uri must be configured to build the token endpoint URL");
        String base = issuerUri.endsWith("/") ? issuerUri : issuerUri + "/";
        return URI.create(base + "oauth/token");
    }

    public Client getClient() {
        return client;
    }

    public String getIssuerUri() {
        return issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getRolesClaim() {
        return rolesClaim;
    }

    public void setRolesClaim(String rolesClaim) {
        this.rolesClaim = rolesClaim;
    }

    public String getPermissionsClaim() {
        return permissionsClaim;
    }

    public void setPermissionsClaim(String permissionsClaim) {
        this.permissionsClaim = permissionsClaim;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public static class Client {
        private String id;
        private String secret;
        private String redirectUri;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }
    }
}
