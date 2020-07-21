package com.example.elderlyversion.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyData {
    @SerializedName("regId")
    @Expose
    private String regId;
    @SerializedName("ekey")
    @Expose
    private Integer ekey;


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Integer getEkey() {
        return ekey;
    }

    public void setEkey(Integer ekey) {
        this.ekey = ekey;
    }

}
