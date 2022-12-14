package com.example.demo.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;

public class MessageDTO {

	private Long messageId;
	private String message;
	private MultipartFile imageUrl;
	private User sender;
	private User receiver;
	private String type;
	private int count;

	public MessageDTO() {

	}

	public MessageDTO(Long messageId, String message, MultipartFile imageUrl, User sender, User receiver, String type, int count) {
		this.messageId = messageId;
		this.message = message;
		this.imageUrl = imageUrl;
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.count=count;
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
	
	

}
