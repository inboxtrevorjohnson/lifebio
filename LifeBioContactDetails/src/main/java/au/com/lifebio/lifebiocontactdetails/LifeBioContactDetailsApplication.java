package au.com.lifebio.lifebiocontactdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LifeBioContactDetailsApplication {


	public static void main(String[] args) {
		SpringApplication.run(LifeBioContactDetailsApplication.class, args);
	}
}
