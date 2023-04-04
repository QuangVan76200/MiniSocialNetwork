package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "confirmPassword")
    private String confirmPassword;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(unique = true, name = "email")
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

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    private List<Message> getFromUserMessagesList = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    public List<Message> getToUserMessagesList = new ArrayList<>();
    
    @JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	List<Comment> comment;

    @Transient
    private boolean enabled;

    public User() {
    }

    public User(Long id, @NotBlank @Size(min = 5, max = 100) String fullName,
            @NotBlank @Size(min = 5, max = 100) String userName, @NotBlank @Size(max = 50) String password,
            String resetPasswordToken,
            @NotBlank @Size(max = 50) @Email @Pattern(regexp = "/^[a-zA-Z0-9_.+-]+@fsoft.com.vn$/") String email,
            String phone, Integer numbOfFollowers, Integer numbOfFollowing, String avatar, Set<Role> roles,
            List<User> followerUsers, List<User> followingUsers, List<Post> likePosts,
            List<Message> getFromUserMessagesList, List<Message> getToUserMessagesList, boolean enabled,
            String confirmPassword) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.resetPasswordToken = resetPasswordToken;
        this.email = email;
        this.phone = phone;
        this.numbOfFollowers = numbOfFollowers;
        this.numbOfFollowing = numbOfFollowing;
        this.avatar = avatar;
        this.roles = roles;
        this.followerUsers = followerUsers;
        this.followingUsers = followingUsers;
        this.likePosts = likePosts;
        this.getFromUserMessagesList = getFromUserMessagesList;
        this.getToUserMessagesList = getToUserMessagesList;
        this.enabled = enabled;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
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

    public Integer getNumbOfFollowers() {
        return numbOfFollowers;
    }

    public void setNumbOfFollowers(Integer numbOfFollowers) {
        this.numbOfFollowers = numbOfFollowers;
    }

    public Integer getNumbOfFollowing() {
        return numbOfFollowing;
    }

    public void setNumbOfFollowing(Integer numbOfFollowing) {
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

    public List<Message> getGetFromUserMessagesList() {
        return getFromUserMessagesList;
    }

    public void setGetFromUserMessagesList(List<Message> getFromUserMessagesList) {
        this.getFromUserMessagesList = getFromUserMessagesList;
    }

    public List<Message> getGetToUserMessagesList() {
        return getToUserMessagesList;
    }

    public void setGetToUserMessagesList(List<Message> getToUserMessagesList) {
        this.getToUserMessagesList = getToUserMessagesList;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatar, email, followerUsers, followingUsers, fullName, getFromUserMessagesList,
                getToUserMessagesList, id, likePosts, numbOfFollowers, numbOfFollowing, password, phone,
                resetPasswordToken, roles, userName);
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
                && Objects.equals(getFromUserMessagesList, other.getFromUserMessagesList)
                && Objects.equals(getToUserMessagesList, other.getToUserMessagesList) && Objects.equals(id, other.id)
                && Objects.equals(likePosts, other.likePosts) && Objects.equals(numbOfFollowers, other.numbOfFollowers)
                && Objects.equals(numbOfFollowing, other.numbOfFollowing) && Objects.equals(password, other.password)
                && Objects.equals(phone, other.phone) && Objects.equals(resetPasswordToken, other.resetPasswordToken)
                && Objects.equals(roles, other.roles) && Objects.equals(userName, other.userName);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", password=" + password
                + ", resetPasswordToken=" + resetPasswordToken + ", email=" + email + ", phone=" + phone
                + ", numbOfFollowers=" + numbOfFollowers + ", numbOfFollowing=" + numbOfFollowing + ", avatar=" + avatar
                + ", roles=" + roles + ", followerUsers=" + followerUsers + ", followingUsers=" + followingUsers
                + ", likePosts=" + likePosts + ", getFromUserMessagesList=" + getFromUserMessagesList
                + ", getToUserMessagesList=" + getToUserMessagesList + "]";
    }

}
