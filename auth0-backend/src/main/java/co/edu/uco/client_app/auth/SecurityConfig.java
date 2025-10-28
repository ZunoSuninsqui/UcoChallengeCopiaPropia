package co.edu.uco.client_app.auth;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationConverter jwtAuthenticationConverter)
            throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/authorized").permitAll()
                        .requestMatchers(HttpMethod.GET, "/list").hasAnyAuthority("SCOPE_read", "ROLE_admin")
                        .requestMatchers(HttpMethod.POST, "/create").hasAnyAuthority("SCOPE_write", "ROLE_admin")
                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(
            @Value("${auth0.roles-claim:}") String rolesClaim,
            @Value("${auth0.permissions-claim:permissions}") String permissionsClaim) {
        JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Set<GrantedAuthority> authorities = new LinkedHashSet<>(scopeConverter.convert(jwt));
            authorities.addAll(fromClaim(jwt, rolesClaim, "ROLE_"));
            authorities.addAll(fromClaim(jwt, "roles", "ROLE_"));
            authorities.addAll(fromClaim(jwt, permissionsClaim, "SCOPE_"));
            return authorities;

        });
        return jwtConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer,
            @Value("${auth0.audience:}") String audience) {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> validator = JwtValidators.createDefaultWithIssuer(issuer);
        if (audience != null && !audience.isBlank()) {
            validator = new DelegatingOAuth2TokenValidator<>(validator, new AudienceValidator(audience));

        }
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;

    }

    private Set<GrantedAuthority> fromClaim(Jwt jwt, String claimName, String prefix) {
        if (claimName == null || claimName.isBlank() || jwt.getClaims().get(claimName) == null) {
            return Set.of();

        }
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(prefix);
        converter.setAuthoritiesClaimName(claimName);
        return new LinkedHashSet<>(converter.convert(jwt));

    }
}
