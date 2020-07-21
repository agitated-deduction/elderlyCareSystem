package com.example.elderlyversion.data;

public class LoginData {
    private String uName;
    private String uBirth;

    public LoginData(String name, String birth){
        uName = name;
        uBirth = birth;
    }

    public String getuName() { return uName; }

    public void setuName(String uName) { this.uName = uName; }

    public String getuBirth() { return uBirth; }

    public void setuBirth(String uBirth) { this.uBirth = uBirth; }
}
