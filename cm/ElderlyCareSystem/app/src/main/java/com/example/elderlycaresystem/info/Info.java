package com.example.elderlycaresystem.info;

public class Info {
    private String name;
    private String pulse;
    private String temperature;
    private String humid;
    private String latitude;//위도
    private String longitude;//경도

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
