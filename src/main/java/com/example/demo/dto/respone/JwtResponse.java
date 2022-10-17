package com.example.demo.dto.respone;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	private Long id;
	private String token;
	private String type = "Bearer";
	private String fullName;
	private Collection<? extends GrantedAuthority> roles;
	
	public JwtResponse () {
		
	}

	public JwtResponse(Long id, String token, String type, String fullName,
			Collection<? extends GrantedAuthority> roles) {
		this.id = id;
		this.token = token;
		this.type = type;
		this.fullName = fullName;
		this.roles = roles;
	}

	public JwtResponse(String token, String fullName, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.fullName = fullName;
		this.roles = authorities;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}
	
	
	

}
