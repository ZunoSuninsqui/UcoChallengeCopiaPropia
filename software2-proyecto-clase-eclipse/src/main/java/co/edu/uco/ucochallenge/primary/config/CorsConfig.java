package co.edu.uco.ucochallenge.primary.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

        private final List<String> allowedOrigins;
        private final List<String> allowedMethods;
        private final List<String> allowedHeaders;
        private final long maxAge;
        private final boolean allowCredentials;

        public CorsConfig(
                        @Value("${app.cors.allowed-origins:*}") String allowedOrigins,
                        @Value("${app.cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}") String allowedMethods,
                        @Value("${app.cors.allowed-headers:*}") String allowedHeaders,
                        @Value("${app.cors.max-age:3600}") long maxAge,
                        @Value("${app.cors.allow-credentials:false}") boolean allowCredentials) {
                this.allowedOrigins = parseConfigList(allowedOrigins);
                this.allowedMethods = parseConfigList(allowedMethods);
                this.allowedHeaders = parseConfigList(allowedHeaders);
                this.maxAge = maxAge;
                this.allowCredentials = allowCredentials;
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                var mapping = registry.addMapping("/**")
                                .allowedOrigins(allowedOrigins.toArray(String[]::new))
                                .allowedMethods(allowedMethods.toArray(String[]::new))
                                .allowedHeaders(allowedHeaders.toArray(String[]::new))
                                .maxAge(maxAge);

                if (allowCredentials) {
                        mapping.allowCredentials(true);
                } else {
                        mapping.allowCredentials(false);
                }
        }

        private List<String> parseConfigList(String value) {
                var parsedValues = Arrays.stream(value.split(","))
                                .map(String::trim)
                                .filter(item -> !item.isEmpty())
                                .collect(Collectors.toList());

                if (parsedValues.isEmpty()) {
                        return List.of("*");
                }

                return parsedValues;
        }
}