package co.edu.uco.parametersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParametersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParametersServiceApplication.class, args);
	}
}
