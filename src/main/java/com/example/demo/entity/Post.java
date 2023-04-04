package com.example.demo.entity;

import java.util.Date;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@NotBlank
	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "likeCount")
	private Integer likeCount;

	@Column(name = "commentCount")
	private Integer commentCount;

	@Column(name = "createdAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date createdAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateLastModified;

	@Column(name = "status")
	private int status;

	@Column(name = "imageUrl")
	private String imageUrl;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "userId")
	private User user;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	List<Comment> comment;

	@ManyToMany
	@JoinTable(name = "Love", joinColumns = @JoinColumn(name = "postId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	List<User> likePost;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "postImage")
	Set<ImageFile> images;

	public Post() {

	}

	public Post(Long postId, @NotBlank String title, String content, Integer likeCount, Integer commentCount,
			Date createdAt, Date dateLastModified, int status, String imageUrl, User user, List<Comment> comment,
			List<User> likePost, Set<ImageFile> images) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.createdAt = createdAt;
		this.dateLastModified = dateLastModified;
		this.status = status;
		this.imageUrl = imageUrl;
		this.user = user;
		this.comment = comment;
		this.likePost = likePost;
		this.images = images;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public List<User> getLikePost() {
		return likePost;
	}

	public void setLikePost(List<User> likePost) {
		this.likePost = likePost;
	}

	public Set<ImageFile> getPostImage() {
		return images;
	}

	public void setPostImage(Set<ImageFile> images) {
		this.images = images;
	}

}