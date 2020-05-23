package org.eltaj.step.Entities;

public class User {
    public int id;
    public String name;
    public String surname;
    public String picture;
    public String password;
    public String email;
    public String lastLogin;

    public User() {
    }

    public User(int id, String name, String surname, String picture) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.picture = picture;
    }

    public User(String name, String surname, String email, String picture, String password) {
        this.name = name;
        this.surname = surname;
        this.picture = picture;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String surname, String picture, String lastLogin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.picture = picture;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }


    @Override
    public String toString() {
        return String.format("%s %s", name, surname);
    }
}
