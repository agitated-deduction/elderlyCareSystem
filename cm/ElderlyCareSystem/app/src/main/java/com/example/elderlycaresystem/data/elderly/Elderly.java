package com.example.elderlycaresystem.data.elderly;

    public class Elderly {
    private int elderlyRole;
    private String elderlyId;
    private String elderlyName;
    private String elderlyTell;
    private String elderlyStat;
    private String elderlyUrl;


    public Elderly(int role,String id, String name, String tell, String stat,String url){
        elderlyRole = role;
        elderlyId = id;
        elderlyName = name;
        elderlyTell = tell;
        elderlyStat = stat;
        elderlyUrl = url;
    }

    public int getElderlyRole() { return elderlyRole; }

    public void setElderlyRole(int elderlyRole) { this.elderlyRole = elderlyRole; }

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

    public String getElderlyUrl() { return elderlyUrl; }

    public void setElderlyUrl(String elderlyUrl) { this.elderlyUrl = elderlyUrl; }
}
