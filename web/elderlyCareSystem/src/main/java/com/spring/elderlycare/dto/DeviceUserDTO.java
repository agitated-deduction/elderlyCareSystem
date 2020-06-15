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
