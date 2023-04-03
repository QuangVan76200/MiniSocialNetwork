package com.example.demo.dto.respone;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;

	private String fullName;

	private String userName;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String confirmPassword;

	@JsonIgnore
	private String resetPasswordToken;

	private String email;

	@JsonIgnore
	private String phone;

	private Integer numbOfFollowers;

	private Integer numbOfFollowing;

	private String avatar;

	private Set<Role> roles;

	private List<Long> followerUsers = new ArrayList<>();

	private List<Long> followingUsers = new ArrayList<>();

	private List<Long> likePosts = new ArrayList<>();
	
	@JsonIgnore
	private List<Long> getFromUserMessagesList = new ArrayList<>();

	@JsonIgnore
	private List<Long> getToUserMessagesList = new ArrayList<>();

	private boolean enabled;
}
