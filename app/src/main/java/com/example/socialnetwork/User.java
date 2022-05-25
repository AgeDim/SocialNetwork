package com.example.socialnetwork;

public class User {
    String name;
    String email;
    String uid;
    String imagePath;

    public String getUid() {
        return uid;
    }

    User(){}

    User(String name, String email, String uid,String Uri) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
