package au.com.lifebio.LifeBioAPIGateway;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.Arrays;

/**
 * Created by Trevor on 2018/08/30.
 */
@Configuration
public class ClientCredentialsConfiguration {

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    private OAuth2ProtectedResourceDetails resource() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("admin");
        resourceDetails.setPassword("admin1234");

        resourceDetails.setAccessTokenUri("http://localhost:4004/oauth/token");
        resourceDetails.setClientId("spring-security-oauth2-read-write-client");
        resourceDetails.setClientSecret("spring-security-oauth2-read-write-client-password1234");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Arrays.asList("read", "write"));
        return resourceDetails;
    }

}