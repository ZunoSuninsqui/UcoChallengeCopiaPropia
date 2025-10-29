package co.edu.uco.client_app.controller;

import co.edu.uco.client_app.models.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(
        origins = {"http://localhost:5173", "http://localhost:5174"},
        allowedHeaders = "*",
        allowCredentials = "true"
)

@RestController
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/list")
    public List<Message> list(HttpServletRequest request, JwtAuthenticationToken authentication) {
        traceInboundRequest("GET", "/list", request, authentication);
        return Collections.singletonList(new Message("Lista de varios elementos"));
    }

    @PostMapping("/create")

    public Message create(
            HttpServletRequest request,
            JwtAuthenticationToken authentication,
            @RequestBody Message message
    ) {
        traceInboundRequest("POST", "/create", request, authentication);
        LOGGER.info("[Backend] Payload recibido en /create: {}", message);
        return message;
    }

    @GetMapping("/authorized")

    public Map<String, String> authorized(
            @RequestParam String code,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization
    ) {
        LOGGER.info("[Backend] Solicitud publica /authorized - token presente: {}", authorization != null);
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/debug/token")
    public Map<String, Object> debugToken(
            HttpServletRequest request,
            JwtAuthenticationToken authentication,
            @RequestHeader(value = "X-Debug-Trace", required = false) String traceId
    ) {
        Jwt jwt = traceInboundRequest("GET", "/debug/token", request, authentication);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("path", "/debug/token");
        payload.put("timestamp", Instant.now().toString());
        payload.put("traceId", Optional.ofNullable(traceId).orElse("frontend"));
        payload.put("authorizationHeader", Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(this::summarizeToken)
                .orElse("none"));

        if (authentication != null) {
            payload.put("principal", Optional.ofNullable(authentication.getName()).orElse("anonymous"));
            payload.put("authorities", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        if (jwt != null) {
            payload.put("subject", jwt.getSubject());
            payload.put("audience", jwt.getAudience());
            payload.put("expiresAt", Optional.ofNullable(jwt.getExpiresAt()).map(Instant::toString).orElse(null));
            payload.put("issuedAt", Optional.ofNullable(jwt.getIssuedAt()).map(Instant::toString).orElse(null));
            payload.put("claims", jwt.getClaims());
        } else {
            payload.put("error", "No se pudo resolver el JWT de la solicitud");
        }

        LOGGER.info("[Backend] Debug token -> {}", payload);
        return payload;
    }

    private Jwt traceInboundRequest(
            String method,
            String path,
            HttpServletRequest request,
            JwtAuthenticationToken authentication
    ) {
        String rawAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        LOGGER.info(
                "[Backend] {} {} recibido - Authorization presente: {}",
                method,
                path,
                rawAuthorization != null
        );

        if (authentication == null) {
            LOGGER.warn("[Backend] No se resolvió JwtAuthenticationToken para la solicitud {} {}", method, path);
            return null;
        }

        Jwt jwt = authentication.getToken();
        if (jwt == null) {
            LOGGER.warn("[Backend] JwtAuthenticationToken sin token para {} {}", method, path);
            return null;
        }

        LOGGER.info(
                "[Backend] JWT aud: {}, subject: {}, exp: {}",
                jwt.getAudience(),
                jwt.getSubject(),
                jwt.getExpiresAt());

        LOGGER.debug("[Backend] Claims JWT: {}", jwt.getClaims());

        Optional.ofNullable(rawAuthorization)
                .map(this::summarizeToken)
                .ifPresent(summary -> LOGGER.info("[Backend] Token adjunto: {}", summary));

        return jwt;
    }

    private String summarizeToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return "sin bearer";
        }

        String token = authorizationHeader.substring("Bearer ".length());
        if (token.length() <= 16) {
            return token;
        }
        return token.substring(0, 8) + "..." + token.substring(token.length() - 8);
    }
}
