package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Users", uniqueConstraints = { @UniqueConstraint(columnNames = { "userName" }),

		@UniqueConstraint(columnNames = { "email" }) })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long id;

	@NotBlank
	@Size(min = 5, max = 100)
	@Column(name = "fullName")
	private String fullName;

	@NotBlank
	@Size(min = 5, max = 100)
	@Column(name = "userName")
	private String userName;

	@NotBlank
	@JsonIgnore
	@Size(max = 50)
	@Column(name = "password")
	private String password;
	
	@Column(name="reset_password_token")
	private String resetPasswordToken;

	@NotBlank
	@Size(max = 50)
	@Email
	@Pattern(regexp="/^[a-zA-Z0-9_.+-]+@fsoft.com.vn$/")  
	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Transient
	private Integer numbOfFollowers;

	@Transient
	private Integer numbOfFollowing;

	@Lob
	@Column(name = "avatar")
	private String avatar;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "rolesId"))
	Set<Role> roles;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "follow_users", joinColumns = @JoinColumn(name = "followed_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private List<User> followerUsers = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "followerUsers")
	private List<User> followingUsers = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "likePost")
	private List<Post> likePosts = new ArrayList<>();

	public User() {
	}

	public User(Long id, @NotBlank @Size(min = 5, max = 100) String fullName,
			@NotBlank @Size(min = 5, max = 100) String userName, @NotBlank @Size(max = 50) String password,
			@NotBlank @Size(max = 50) @Email String email, String phone, int numbOfFollowers, int numbOfFollowing,
			String avatar, Set<Role> roles, List<User> followerUsers, List<User> followingUsers, List<Post> likePosts) {
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.numbOfFollowers = numbOfFollowers;
		this.numbOfFollowing = numbOfFollowing;
		this.avatar = avatar;
		this.roles = roles;
		this.followerUsers = followerUsers;
		this.followingUsers = followingUsers;
		this.likePosts = likePosts;
	}

	public User(@NotBlank @Size(min = 5, max = 100) String fullName,
			@NotBlank @Size(min = 5, max = 100) String userName, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank String encode) {
		this.fullName = fullName;
		this.userName = userName;
		this.password = encode;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getNumbOfFollowers() {
		return numbOfFollowers;
	}

	public void setNumbOfFollowers(int numbOfFollowers) {
		this.numbOfFollowers = numbOfFollowers;
	}

	public int getNumbOfFollowing() {
		return numbOfFollowing;
	}

	public void setNumbOfFollowing(int numbOfFollowing) {
		this.numbOfFollowing = numbOfFollowing;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<User> getFollowerUsers() {
		return followerUsers;
	}

	public void setFollowerUsers(List<User> followerUsers) {
		this.followerUsers = followerUsers;
	}

	public List<User> getFollowingUsers() {
		return followingUsers;
	}

	public void setFollowingUsers(List<User> followingUsers) {
		this.followingUsers = followingUsers;
	}

	public List<Post> getLikePosts() {
		return likePosts;
	}

	public void setLikePosts(List<Post> likePosts) {
		this.likePosts = likePosts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public void setNumbOfFollowers(Integer numbOfFollowers) {
		this.numbOfFollowers = numbOfFollowers;
	}

	public void setNumbOfFollowing(Integer numbOfFollowing) {
		this.numbOfFollowing = numbOfFollowing;
	}

	@Override
	public int hashCode() {
		return Objects.hash(avatar, email, followerUsers, followingUsers, fullName, id, likePosts, numbOfFollowers,
				numbOfFollowing, password, phone, roles, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(avatar, other.avatar) && Objects.equals(email, other.email)
				&& Objects.equals(followerUsers, other.followerUsers)
				&& Objects.equals(followingUsers, other.followingUsers) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(id, other.id) && Objects.equals(likePosts, other.likePosts)
				&& numbOfFollowers == other.numbOfFollowers && numbOfFollowing == other.numbOfFollowing
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone)
				&& Objects.equals(roles, other.roles) && Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", password=" + password
				+ ", email=" + email + ", phone=" + phone + ", numbOfFollowers=" + numbOfFollowers
				+ ", numbOfFollowing=" + numbOfFollowing + ", avatar=" + avatar + ", roles=" + roles
				+ ", followerUsers=" + followerUsers + ", followingUsers=" + followingUsers + ", likePosts=" + likePosts
				+ "]";
	}

	

}
