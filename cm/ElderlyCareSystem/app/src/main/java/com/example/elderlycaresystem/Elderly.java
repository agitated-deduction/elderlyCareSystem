package com.example.elderlycaresystem;

public class Elderly {
    public String elderlyId;
    public String elderlyName;
    public String elderlyTell;
    public String elderlyStat;
    public Elderly(String id, String name, String tell, String stat){
        elderlyId = id;
        elderlyName = name;
        elderlyTell = tell;
        elderlyStat = stat;
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
        return elderlyStat;
    }
}
