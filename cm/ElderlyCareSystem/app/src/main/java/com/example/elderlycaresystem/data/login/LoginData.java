package com.example.elderlycaresystem.data.login;

public class LoginData {
    String uid;
    String upwd;
    public LoginData(String id, String pwd){
        this.uid = id;
        this.upwd = pwd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
}
