package limitless.com.br.parse.model;

/**
 * Created by Márcio Sn on 22/12/2015.
 */
public class NotificationObject {

    private Long id;
    private String message;
    private String timestamp;

    public NotificationObject() {}

    public NotificationObject(Long id, String message, String timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NotificationObject{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

