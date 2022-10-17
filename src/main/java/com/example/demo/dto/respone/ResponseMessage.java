package com.example.demo.dto.respone;

public class ResponseMessage extends Exception{
	private String message;
	
	public ResponseMessage() {
		
	}

	public ResponseMessage(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
