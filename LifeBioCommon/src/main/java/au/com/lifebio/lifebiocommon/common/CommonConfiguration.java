package au.com.lifebio.lifebiocommon.common;

import au.com.lifebio.lifebiocommon.common.exception.CommonRestResponseEntityExceptionHandler;
import org.springframework.context.annotation.Bean;

public class CommonConfiguration {

    @Bean
    public CommonRestResponseEntityExceptionHandler commonRestResponseEntityExceptionHandler(){
        return new CommonRestResponseEntityExceptionHandler();
    }

}