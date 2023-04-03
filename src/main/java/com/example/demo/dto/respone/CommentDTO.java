package com.example.demo.dto.respone;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.demo.entity.ImageFile;
import com.example.demo.entity.Post;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDTO {

	private Long commentId;

	private String text;

	private Date created;

	private Date modifiedDate;

	private Integer likeCount;

	private UserDTO user;

	private Post post;

	private List<UserDTO> likeList;

	private String imageUrl;

	Set<ImageFile> images;

	public CommentDTO(Long commentId, String text, Date created, Date modifiedDate, Integer likeCount, UserDTO user,
			Post post, List<UserDTO> likeList, String imageUrl, Set<ImageFile> images) {
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

	public CommentDTO() {
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<UserDTO> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<UserDTO> likeList) {
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

}
