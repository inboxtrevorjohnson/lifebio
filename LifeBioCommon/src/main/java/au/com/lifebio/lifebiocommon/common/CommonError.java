package au.com.lifebio.lifebiocommon.common;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Trevor on 2018/07/27.
 */
public class CommonError {

    private HttpStatus status;
    private int statusCode;
    private String reasonPhrase;
    private String message;
    private List<String> errors;

    public CommonError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.statusCode = status.value();
        this.reasonPhrase = status.getReasonPhrase();
        this.message = message;
        this.errors = errors;
    }

    public CommonError(HttpStatus status, String message, String error) {
        this.status = status;
        this.statusCode = status.value();
        this.reasonPhrase = status.getReasonPhrase();
        this.message = message;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}