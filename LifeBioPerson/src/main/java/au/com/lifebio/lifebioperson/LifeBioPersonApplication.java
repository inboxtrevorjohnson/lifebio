package au.com.lifebio.lifebioperson;

import au.com.lifebio.lifebiocommon.common.CommonConfiguration;
import au.com.lifebio.lifebiocommon.common.exception.CommonRestResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class LifeBioPersonApplication extends CommonConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(LifeBioPersonApplication.class, args);
	}

}
