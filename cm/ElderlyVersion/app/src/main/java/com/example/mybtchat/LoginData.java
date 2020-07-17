package com.example.mybtchat;

public class LoginData {
    private String uid;
    private String ubirth;

    public LoginData(String id, String birth){
        uid = id;
        ubirth = birth;
    }

    public String getUid() {
        return uid;
    }

    public String getUbirth() {
        return ubirth;
    }
}
