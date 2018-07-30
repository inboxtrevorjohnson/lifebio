package au.com.lifebio.lifebiocommon.common.exception;

import au.com.lifebio.lifebiocommon.common.CommonError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trevor on 2018/07/05.
 */
@ControllerAdvice
public class CommonRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public CommonRestResponseEntityExceptionHandler(){

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
        HttpHeaders headers,  HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
                errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        CommonError commonError =
            new CommonError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return handleExceptionInternal(ex, commonError, headers, commonError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter( MissingServletRequestParameterException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        CommonError commonError = new CommonError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<Object>( commonError, new HttpHeaders(), commonError.getStatus());
    }

    @ExceptionHandler({ org.springframework.dao.DataIntegrityViolationException.class })
    public ResponseEntity<CommonError> handleConstraintViolation( Exception ex, WebRequest request) {
        String error = ex.getMessage();

        CommonError commonError = new CommonError(HttpStatus.CONFLICT, "Data Integrity Violation", error);

        return new ResponseEntity<CommonError>( commonError, new HttpHeaders(), commonError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch( MethodArgumentTypeMismatchException ex,
                                                                    WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        CommonError commonError = new CommonError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<Object>(commonError, new HttpHeaders(), commonError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        CommonError commonError = new CommonError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);

        return new ResponseEntity<Object>(commonError, new HttpHeaders(), commonError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported( HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        CommonError commonError = new CommonError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
                builder.toString());

        return new ResponseEntity<Object>(commonError, new HttpHeaders(), commonError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported( HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");

        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        CommonError commonError = new CommonError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
                builder.substring(0, builder.length() - 2));

        return new ResponseEntity<Object>( commonError, new HttpHeaders(), commonError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        CommonError commonError = new CommonError(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>( commonError, new HttpHeaders(), commonError.getStatus());
    }

    @ExceptionHandler({ ConflictException.class, CreationException.class, ModificationException.class,
        RemoveException.class, ResourceNotFoundException.class, TypeNotSupportedException.class})
    public ResponseEntity<Object> handleApplicationSpecificException( Exception ex, WebRequest request) {
        String error = ex.getMessage();

        CommonError commonError = new CommonError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return new ResponseEntity<Object>(commonError, new HttpHeaders(), commonError.getStatus());
    }

}
