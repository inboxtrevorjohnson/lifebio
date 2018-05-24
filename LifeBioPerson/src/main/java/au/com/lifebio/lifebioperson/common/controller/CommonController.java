package au.com.lifebio.lifebioperson.common.controller;

import au.com.lifebio.lifebioperson.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The <code>CommonController</code> is used as a parent for
 * all controllers to provide common functionality and utilities.
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */
public class CommonController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error resourceNotFound(ResourceNotFoundException exception) {
        return new Error(exception.getMessage());
    }

}
