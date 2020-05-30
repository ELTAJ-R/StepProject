package org.eltaj.step.DataBase.Updater;

import org.eltaj.step.DataBase.HerokuEnv;


public class InitializeSQL {
    //Please add UserName, Password and URL environment variables in order to access db.
    private final static HerokuEnv credentials=new HerokuEnv();
    private final String uname=credentials.jdbc_username();
    private final String URL=credentials.jdbc_url();
    private final String parol=credentials.jdbc_password();

    public void autoUpdate(){
        FlywayMethod.prepare(
                URL,
                uname,
                parol);}}
