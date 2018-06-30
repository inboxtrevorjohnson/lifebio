package au.com.lifebio.lifebiocontactdetails.exception;

/**
 * Created by Trevor on 2018/06/18.
 */
public class TypeNotSupportedException extends RuntimeException {

    private String message;

    public TypeNotSupportedException(String message){
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
            return "Type Not Found!";
        }
        return message;
    }
}
