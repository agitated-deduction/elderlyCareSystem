package com.spring.elderlycare.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("DeviceUserDTO")
public class DeviceUserDTO {
	private int dkey;
	private String dname;
	private Date dbirth;
	private String dtel;
	private String daddr;
	private String staff;
	private String relative;
	private String homeIoT;
	private String bandIoT;
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getRelative() {
		return relative;
	}
	public void setRelative(String relative) {
		this.relative = relative;
	}
	public String getHomeIoT() {
		return homeIoT;
	}
	public void setHomeIoT(String homeIoT) {
		this.homeIoT = homeIoT;
	}
	public String getBandIoT() {
		return bandIoT;
	}
	public void setBandIoT(String bandIoT) {
		this.bandIoT = bandIoT;
	}
	public int getDkey() {
		return dkey;
	}
	public void setDkey(int dkey) {
		this.dkey = dkey;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Date getDbirth() {
		return dbirth;
	}
	public void setDbirth(Date dbirth) {
		this.dbirth = dbirth;
	}
	public String getDtel() {
		return dtel;
	}
	public void setDtel(String dtel) {
		this.dtel = dtel;
	}
	public String getDaddr() {
		return daddr;
	}
	public void setDaddr(String daddr) {
		this.daddr = daddr;
	}
	
	
}
