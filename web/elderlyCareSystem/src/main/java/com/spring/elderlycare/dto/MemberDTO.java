package com.spring.elderlycare.dto;

import org.springframework.stereotype.Component;

@Component("MemberDTO")
public class MemberDTO {
	private String m_id;
	private String m_pwd;
	private String m_name;
	private String m_tel;
	private String m_email;
	private String m_role;
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_pwd() {
		return m_pwd;
	}
	public void setM_pwd(String m_pwd) {
		this.m_pwd = m_pwd;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_tel() {
		return m_tel;
	}
	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_role() {
		return m_role;
	}
	public void setM_role(String m_role) {
		this.m_role = m_role;
	}
	
}
