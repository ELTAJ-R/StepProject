package DataBase;

public class Triplet {
    int id;
    String sender;
    String message;

    public Triplet() {
    }

    public Triplet(int id, String sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    //this method determines how messages are going to be shown on web-page
    @Override
    public String toString() {
        return String.format("%s: %s", sender, message);
    }
}
