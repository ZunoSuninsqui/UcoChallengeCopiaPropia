package co.edu.uco.client_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientAppApplication.class, args);
	}
}
