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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "chat_group")
public class GroupEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;

	@Column(name = "name")
	private String name;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "groupImage")
	@JsonManagedReference
	Set<ImageFile> images;
	
	@Column(name = "imageUrl")
	private String imageUrl;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "groupUser", joinColumns = @JoinColumn(name = "groupId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private Set<User> listUser = new HashSet<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "groupChat", joinColumns = @JoinColumn(name = "groupId"), inverseJoinColumns = @JoinColumn(name = "messageId"))
	private Set<Message> groupChat;

	public GroupEntity() {

	}

	public GroupEntity(int groupId, String name, Set<ImageFile> images, String imageUrl, Set<User> listUser,
			Set<Message> groupChat) {
		this.groupId = groupId;
		this.name = name;
		this.images = images;
		this.imageUrl = imageUrl;
		this.listUser = listUser;
		this.groupChat = groupChat;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<User> getListUser() {
		return listUser;
	}

	public void setListUser(Set<User> listUser) {
		this.listUser = listUser;
	}

	public Set<Message> getGroupChat() {
		return groupChat;
	}

	public void setGroupChat(Set<Message> groupChat) {
		this.groupChat = groupChat;
	}

	

}
