package com.example.demo.entity;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(name = "text")
	private String text;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "created")
	private Date created;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "modifiedDate")
	private Date modifiedDate;

	@Column(name = "likeCount")
	private Integer likeCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "userId")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	@JsonIgnore
	private Post post;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "comment_likes", joinColumns = @JoinColumn(name = "commentId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private List<User> likeList;

	@Column(name = "imageUrl")
	private String imageUrl;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commentImage")
	Set<ImageFile> images;

	public Comment() {
	}

	public Comment(Long commentId, String text, Date created, Date modifiedDate, Integer likeCount, User user,
			Post post, List<User> likeList, String imageUrl, Set<ImageFile> images) {
		this.commentId = commentId;
		this.text = text;
		this.created = created;
		this.modifiedDate = modifiedDate;
		this.likeCount = likeCount;
		this.user = user;
		this.post = post;
		this.likeList = likeList;
		this.imageUrl = imageUrl;
		this.images = images;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<User> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<User> likeList) {
		this.likeList = likeList;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Set<ImageFile> getImages() {
		return images;
	}

	public void setImages(Set<ImageFile> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", text=" + text + ", created=" + created + ", modifiedDate="
				+ modifiedDate + ", likeCount=" + likeCount + ", user=" + user + ", post=" + post + ", likeList="
				+ likeList + "]";
	}

}
