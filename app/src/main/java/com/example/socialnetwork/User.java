package com.example.socialnetwork;

public class User {
    String name;
    String email;
    String uid;
    UserConfig config;

    public String getUid() {
        return uid;
    }

    User(){}

    User(String name, String email, String uid,UserConfig config) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
