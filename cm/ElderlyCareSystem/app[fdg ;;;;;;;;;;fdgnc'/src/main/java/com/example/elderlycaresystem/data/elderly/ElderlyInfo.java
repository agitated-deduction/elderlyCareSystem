package com.example.elderlycaresystem.data.elderly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElderlyInfo {

//    @SerializedName("ekey")
//    @Expose
//    private Integer ekey;
//    @SerializedName("ename")
//    @Expose
//    private String ename;
//    @SerializedName("ebirth")
//    @Expose
//    private String ebirth;
//    @SerializedName("etel")
//    @Expose
//    private String etel;
//    @SerializedName("eaddr")
//    @Expose
//    private String eaddr;
//

    private int ekey;
    private String ename;
    private String ebirth;
    private String etel;
    private String eaddr;

    public int getEkey() {
        return ekey;
    }

    public void setEkey(int ekey) {
        this.ekey = ekey;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEbirth() {
        return ebirth;
    }

    public void setEbirth(String ebirth) {
        this.ebirth = ebirth;
    }

    public String getEtel() {
        return etel;
    }

    public void setEtel(String etel) {
        this.etel = etel;
    }

    public String getEaddr() {
        return eaddr;
    }

    public void setEaddr(String eaddr) {
        this.eaddr = eaddr;
    }
}
