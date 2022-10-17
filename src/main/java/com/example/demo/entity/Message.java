package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@Column(name = "message")
	private String message;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "messageImage")
	Set<ImageFile> images;

	@Column(name = "imageUrl")
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "type")
	private String type;

	@Transient
	private MessageStatus status;

	@JsonIgnore
	@ManyToMany(mappedBy = "groupChat")
	Set<GroupEntity> groupChat = new HashSet<>();

	public Message() {

	}

	public Message(Long messageId, String message, Set<ImageFile> images, String imageUrl, User user, String type,
			MessageStatus status, Set<GroupEntity> groupChat) {
		this.messageId = messageId;
		this.message = message;
		this.images = images;
		this.imageUrl = imageUrl;
		this.user = user;
		this.type = type;
		this.status = status;
		this.groupChat = groupChat;
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

	public Set<ImageFile> getImages() {
		return images;
	}

	public void setImages(Set<ImageFile> images) {
		this.images = images;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
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

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public Set<GroupEntity> getGroupChat() {
		return groupChat;
	}

	public void setGroupChat(Set<GroupEntity> groupChat) {
		this.groupChat = groupChat;
	}

}
