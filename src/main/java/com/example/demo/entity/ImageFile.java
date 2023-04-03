package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "imageFile")
public class ImageFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postImageId;

	@NotBlank
	@Size(min = 5, max = 100)
	@Column(name = "title")
	private String title;

	@Lob
	@Column(name = "files", length = 50000000)
	private byte[] files;

	@Column(name = "type")
	private String type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "postId")
	@JsonIgnore
	private Post postImage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	@JsonIgnore
	private Product productImage;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "messageId")
	@JsonIgnore
	private Message messageImage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commentId")
	@JsonIgnore
	private Comment commentImage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId")
	@JsonIgnore
	private GroupEntity groupImage;

	public ImageFile() {

	}

	public ImageFile(@NotBlank @Size(min = 5, max = 100) String title, byte[] files, String type) {
		this.title = title;
		this.files = files;
		this.type = type;
	}

	public ImageFile(Long postImageId, @NotBlank @Size(min = 5, max = 100) String title, byte[] files, String type,
			Post postImage, Message messageImage, Comment commentImage) {
		super();
		this.postImageId = postImageId;
		this.title = title;
		this.files = files;
		this.type = type;
		this.postImage = postImage;
		this.messageImage = messageImage;
		this.commentImage = commentImage;
	}

	public Long getPostImageId() {
		return postImageId;
	}

	public Message getMessageImage() {
		return messageImage;
	}

	public void setMessageImage(Message messageImage) {
		this.messageImage = messageImage;
	}

	public void setPostImageId(Long postImageId) {
		this.postImageId = postImageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getFiles() {
		return files;
	}

	public void setFiles(byte[] files) {
		this.files = files;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Post getPostImage() {
		return postImage;
	}

	public void setPostImage(Post postImage) {
		this.postImage = postImage;
	}

	public Comment getCommentImage() {
		return commentImage;
	}

	public void setCommentImage(Comment commentImage) {
		this.commentImage = commentImage;
	}

}