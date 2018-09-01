package au.com.lifebio.lifebioperson;

import au.com.lifebio.lifebiocommon.common.CommonConfiguration;
import au.com.lifebio.lifebiocommon.common.exception.CommonRestResponseEntityExceptionHandler;
import au.com.lifebio.lifebiocommon.common.security.ResourceServerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@SpringBootApplication
@EnableOAuth2Sso
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
@Import(ResourceServerConfiguration.class)
public class LifeBioPersonApplication extends CommonConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(LifeBioPersonApplication.class, args);
	}

}
