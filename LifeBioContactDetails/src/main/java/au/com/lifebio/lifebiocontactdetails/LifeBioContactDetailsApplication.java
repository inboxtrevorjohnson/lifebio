package au.com.lifebio.lifebiocontactdetails;

import au.com.lifebio.lifebiocommon.common.security.ResourceServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@SpringBootApplication
@EnableOAuth2Sso
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
@Import(ResourceServerConfiguration.class)
public class LifeBioContactDetailsApplication {


	public static void main(String[] args) {
		SpringApplication.run(LifeBioContactDetailsApplication.class, args);
	}
}
