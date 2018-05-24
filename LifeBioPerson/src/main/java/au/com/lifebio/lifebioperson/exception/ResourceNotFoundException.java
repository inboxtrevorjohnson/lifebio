package au.com.lifebio.lifebioperson.exception;

/**
 * The <code>ResourceNotFoundException</code> is used as the 'exception handler' to
 * handle all cases where a resource can not be found.
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */
public class ResourceNotFoundException extends RuntimeException {

    private String message;

    public ResourceNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        if(message == null || message.length() > 1){
            return "Resource Not Found!";
        }
        return message;
    }
}
