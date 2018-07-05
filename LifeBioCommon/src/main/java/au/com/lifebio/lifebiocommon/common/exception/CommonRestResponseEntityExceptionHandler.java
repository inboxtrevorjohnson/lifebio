package au.com.lifebio.lifebiocommon.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Trevor on 2018/07/05.
 */
@ControllerAdvice
public class CommonRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private String bodyOfResponse = "CONFLICT Update this error message....";
    private String resourceNotFoundResponse = "NOT FOUND Update this error message....";
    private String typeNotSupporteddResponse = "BAD REQUEST Update this error message....";

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, resourceNotFoundResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { TypeNotSupportedException.class })
    protected ResponseEntity<Object> handleTypeNotSupported(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, typeNotSupporteddResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
