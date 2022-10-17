package com.example.demo.dto.request;

import java.util.Set;

public class SignUpForm {

	private String fullName;
	private String userName;
	private String password;
	private String email;
	private Set<String> roles;
	
	
	public SignUpForm() {
		
	}
	
	
	public SignUpForm(String fullName, String userName, String password, String email, Set<String> roles) {
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


	
}
