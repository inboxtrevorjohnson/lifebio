package au.com.lifebio.lifebiocontactdetails.exception;

/**
 * Created by Trevor on 2018/06/18.
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
