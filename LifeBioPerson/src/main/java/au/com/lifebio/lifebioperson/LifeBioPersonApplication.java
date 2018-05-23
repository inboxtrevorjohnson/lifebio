package au.com.lifebio.lifebioperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LifeBioPersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeBioPersonApplication.class, args);
	}
}
