package com.spring.elderlycare.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("DeviceUserDTO")
public class ElderlyDTO {
	private int ekey;
	private String ename;
	private Date ebirth;
	private String etel;
	private String eaddr;
	
	public int getEkey() {
		return ekey;
	}
	public void setEkey(int ekey) {
		this.ekey = ekey;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Date getEbirth() {
		return ebirth;
	}
	public void setEbirth(Date ebirth) {
		this.ebirth = ebirth;
	}
	public String getEtel() {
		return etel;
	}
	public void setEtel(String etel) {
		this.etel = etel;
	}
	public String getEaddr() {
		return eaddr;
	}
	public void setEaddr(String eaddr) {
		this.eaddr = eaddr;
	}
		
}
