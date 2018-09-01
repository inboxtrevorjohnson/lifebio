package au.com.lifebio.LifeBioAuthorizationServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Trevor on 2018/08/25.
 */
@Configuration
public class Encoders {
    @Bean
    public BCryptPasswordEncoder oauthClientPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
    @Bean
    public BCryptPasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
