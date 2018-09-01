package au.com.lifebio.LifeBioAuthorizationServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LifeBioAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeBioAuthorizationServerApplication.class, args);
	}
}
