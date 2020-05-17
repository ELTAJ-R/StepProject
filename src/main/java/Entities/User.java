package Entities;

public class User {
    public int id;
    public String name;
    public String surname;
    public String picture;

    public User() {
    }

    public User(int id, String name, String surname, String picture) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.picture = picture;
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



    @Override
    public String toString() {
        return String.format("%s %s", name, surname);
    }
}
