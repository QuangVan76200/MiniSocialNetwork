package com.example.demo.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;

public class MessageDTO {

	private Long messageId;
	private String message;
	private MultipartFile imageUrl;
	private User user;
	private String type;
	
	public MessageDTO() {
		
	}
	
	public MessageDTO(Long messageId, String message, MultipartFile imageUrl, User user, String type) {
		this.messageId = messageId;
		this.message = message;
		this.imageUrl = imageUrl;
		this.user = user;
		this.type = type;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MultipartFile getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(MultipartFile imageUrl) {
		this.imageUrl = imageUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
