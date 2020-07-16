package com.example.elderlycaresystem.data.info;

public class ElderlyData {
    private int estep ;
    private String epulse;
    private String ekcal;
    private double latitude;//위도
    private double longitude;//경도

    public ElderlyData() {
        super();
    }

    public Integer getEstep() { return estep; }

    public void setEstep(Integer estep) { this.estep = estep; }

    public String getEpulse() { return epulse; }

    public void setEpulse(String epulse) { this.epulse = epulse; }

    public String getEkcal() { return ekcal; }

    public void setEkcal(String ekcal) { this.ekcal = ekcal; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
