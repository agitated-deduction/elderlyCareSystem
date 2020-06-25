package com.example.retrofitpractice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("upwd")
    @Expose
    private String upwd;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("utel")
    @Expose
    private String utel;
    @SerializedName("uemail")
    @Expose
    private String uemail;
    @SerializedName("urole")
    @Expose
    private Integer urole;

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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUtel() {
        return utel;
    }

    public void setUtel(String utel) {
        this.utel = utel;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public Integer getUrole() {
        return urole;
    }

    public void setUrole(Integer urole) {
        this.urole = urole;
    }

}