package au.com.lifebio.lifebiocommon.common.exception;

/**
 * Created by Trevor on 2018/07/19.
 */
public class ConflictException extends RuntimeException {

    private String message;

    public ConflictException(String message){
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
            return "Conflict Exception!";
        }
        return message;
    }
}
