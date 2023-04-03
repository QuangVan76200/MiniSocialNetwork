package com.example.demo.dto.respone;


public class PostDTO {

	private Long id;
	private String title;
	private String content;
	private String imageUrl;
	private Integer likeCount;
	private Integer commentCount;
	private UserDTO user;

	// Constructors
	public PostDTO() {
	}

	public PostDTO(Long id, String title, String content, String imageUrl, Integer likeCount, Integer commentCount,
			UserDTO user) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.user = user;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
