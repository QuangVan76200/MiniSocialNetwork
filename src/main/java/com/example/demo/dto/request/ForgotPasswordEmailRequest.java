package com.example.demo.dto.request;

public class ForgotPasswordEmailRequest  {
	
private String forgotPasswordEmail;
    
    private String newPassword;
	
	public ForgotPasswordEmailRequest() {
	}

	public String getForgotPasswordEmail() {
		return forgotPasswordEmail;
	}

	public void setForgotPasswordEmail(String forgotPasswordEmail) {
		this.forgotPasswordEmail = forgotPasswordEmail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	
	

}
