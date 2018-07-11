package au.com.lifebio.lifebiocommon.common.exception;

/**
 * Created by Trevor on 2018/07/09.
 */
public class RemoveException extends RuntimeException {

    private String message;

    public RemoveException(String message){
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
            return "Remove Exception!";
        }
        return message;
    }
}
