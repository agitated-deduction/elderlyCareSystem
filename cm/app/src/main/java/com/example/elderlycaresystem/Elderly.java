package com.example.elderlycaresystem;

import android.os.Parcelable;

public class Elderly {
    public String elderlyId;
    public String elderlyName;
    public String elderlyTell;
    public String elderlyAddr;
    public Elderly(String id, String name, String tell, String addr){
        elderlyId = id;
        elderlyName = name;
        elderlyTell = tell;
        elderlyAddr = addr;
    }
    public String getElderlyId(){
        return elderlyId;
    }
    public String getElderlyName(){
        return elderlyName;
    }
    public String getElderlyTell(){
        return elderlyTell;
    }
    public String getElderlyAddr(){
        return elderlyAddr;
    }

}
