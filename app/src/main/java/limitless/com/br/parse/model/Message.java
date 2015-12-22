package limitless.com.br.parse.model;

/**
 * Created by Márcio Sn on 22/12/2015.
 */
public class Message {

    private String message;
    private long timestamp;

    public Message() {
    }

    public Message(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
