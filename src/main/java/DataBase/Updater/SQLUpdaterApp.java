package DataBase.Updater;
public class SQLUpdaterApp {
    //Please add UserName, Password and URL in order to access db.
    private final static String uname = "";
    private final static String parol = "";
    private final static String URL = "";
    public static void main(String[] args) {
       MainLogic.prepare(
                URL,
                uname,
                parol
        );

    }
}
