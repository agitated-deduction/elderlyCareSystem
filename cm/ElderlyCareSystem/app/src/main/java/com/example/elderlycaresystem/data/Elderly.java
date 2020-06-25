package com.example.elderlycaresystem.data;

public class Elderly {
    private String elderlyId;
    private String elderlyName;
    private String elderlyTell;
    private String elderlyStat;

    public Elderly(String id, String name, String tell, String stat){
        elderlyId = id;
        elderlyName = name;
        elderlyTell = tell;
        elderlyStat = stat;
    }

    public String getElderlyId() {
        return elderlyId;
    }

    public void setElderlyId(String elderlyId) {
        this.elderlyId = elderlyId;
    }

    public String getElderlyName() {
        return elderlyName;
    }

    public void setElderlyName(String elderlyName) {
        this.elderlyName = elderlyName;
    }

    public String getElderlyTell() {
        return elderlyTell;
    }

    public void setElderlyTell(String elderlyTell) {
        this.elderlyTell = elderlyTell;
    }

    public String getElderlyStat() {
        return elderlyStat;
    }

    public void setElderlyStat(String elderlyStat) {
        this.elderlyStat = elderlyStat;
    }
}
