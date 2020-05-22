package DataBase.Updater;

import DataBase.HerokuEnv;

public class SQLUpdaterApp {
    //Please add UserName, Password and URL in order to access db.
    public static HerokuEnv credentials=new HerokuEnv();
    private final static String uname = credentials.jdbc_username();
    private final static String parol = credentials.jdbc_password();
    private final static String URL = credentials.jdbc_url();
    public static void autoUpdate(){
       MainLogic.prepare(
               URL,
               uname,
               parol
       );
   }
}
