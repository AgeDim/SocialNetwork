package com.example.socialnetwork;

public class User {
    String name;
    String email;
    String uid;

    public String getUid() {
        return uid;
    }

    User(){}

    User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
