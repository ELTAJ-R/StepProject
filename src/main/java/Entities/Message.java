package Entities;

public class Message {

    public int id;
    public String sender;
    public String message;
    public String isSent;

    public Message() {
    }

    public Message(int id, String sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    public Message(int id, String sender, String message, String isSent) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.isSent = isSent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsSent() {
        return isSent;
    }

    public void setIsSent(String isSent) {
        this.isSent = isSent;
    }

    //this method determines how messages are going to be shown on web-page
    @Override
    public String toString() {
        return String.format("%s: %s", sender, message);
    }
}
