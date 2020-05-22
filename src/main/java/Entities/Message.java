package Entities;

public class Message {

    public int id;
    public String sender;
    public String message;
    public String isSent;
    public String date;

    public Message() {
    }

    public Message(int id, String sender, String message, String isSent, String date) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.isSent = isSent;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //this method determines how messages are going to be shown on web-page
    @Override
    public String toString() {
        return String.format("%s: %s", sender, message);
    }
}
