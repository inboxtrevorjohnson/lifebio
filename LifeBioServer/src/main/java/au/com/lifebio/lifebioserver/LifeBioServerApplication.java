package au.com.lifebio.lifebioserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LifeBioServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeBioServerApplication.class, args);
	}
}
