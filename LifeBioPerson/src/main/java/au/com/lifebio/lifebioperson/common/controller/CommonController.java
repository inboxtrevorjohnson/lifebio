package au.com.lifebio.lifebioperson.common.controller;

import au.com.lifebio.lifebioperson.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Trevor on 2018/06/18.
 */
public class CommonController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error resourceNotFound(ResourceNotFoundException exception) {
        return new Error(exception.getMessage());
    }

}
