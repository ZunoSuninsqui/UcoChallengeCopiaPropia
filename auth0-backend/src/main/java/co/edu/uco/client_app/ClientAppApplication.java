package co.edu.uco.client_app;

import co.edu.uco.client_app.auth.Auth0Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(Auth0Properties.class)
public class ClientAppApplication {
        public static void main(String[] args) {
                SpringApplication.run(ClientAppApplication.class, args);
        }
}
