package au.com.lifebio.lifebiocommon.common.exception;

/**
 * Created by Trevor on 2018/06/18.
 */
public class CreationException extends RuntimeException {

    private String message;

    public CreationException(String message){
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
            return "Creation Exception!";
        }
        return message;
    }
}
