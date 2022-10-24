package com.example.demo.entity;

import java.time.LocalDateTime;
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
	@JoinColumn(name = "from_user_id", referencedColumnName = "userId")
	private User fromUser;

	@ManyToOne
	@JoinColumn(name = "to_user_id", referencedColumnName = "userId")
	private User toUser;

	@Column(name = "type")
	private String type;

	@Column(name = "messageStatus")
	private int messageStatus;

	@Transient
	private MessageStatus statusChat;

	@Column(name = "time")
	private LocalDateTime time;

	@JsonIgnore
	@ManyToMany(mappedBy = "groupChat")
	Set<GroupEntity> groupChat = new HashSet<>();

	public Message() {

	}

	public Message(Long messageId, String message, Set<ImageFile> images, String imageUrl, User fromUser, User toUser,
			String type, int messageStatus, MessageStatus statusChat, LocalDateTime time, Set<GroupEntity> groupChat) {
		this.messageId = messageId;
		this.message = message;
		this.images = images;
		this.imageUrl = imageUrl;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.type = type;
		this.messageStatus = messageStatus;
		this.statusChat = statusChat;
		this.time = time;
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

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(int messageStatus) {
		this.messageStatus = messageStatus;
	}

	public MessageStatus getStatusChat() {
		return statusChat;
	}

	public void setStatusChat(MessageStatus statusChat) {
		this.statusChat = statusChat;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Set<GroupEntity> getGroupChat() {
		return groupChat;
	}

	public void setGroupChat(Set<GroupEntity> groupChat) {
		this.groupChat = groupChat;
	}

}
