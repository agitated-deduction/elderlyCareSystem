package com.spring.elderlycare.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("DatasDTO")
public class DatasDTO {
	private int elderly;
	private float humid;
	private float temp;
	private boolean gas;
	private Timestamp measuredtime;
	
	public int getElderly() {
		return elderly;
	}
	public void setElderly(int elderly) {
		this.elderly = elderly;
	}
	public float getHumid() {
		return humid;
	}
	public void setHumid(float humid) {
		this.humid = humid;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public boolean isGas() {
		return gas;
	}
	public void setGas(boolean gas) {
		this.gas = gas;
	}
	public Timestamp getMeasuredtime() {
		return measuredtime;
	}
	public void setMeasuredtime(Timestamp measuredtime) {
		this.measuredtime = measuredtime;
	}
	
	
}
