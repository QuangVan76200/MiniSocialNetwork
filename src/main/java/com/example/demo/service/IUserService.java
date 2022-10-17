package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.User;

@Component
public interface IUserService {

	Optional<User> getAuthenticatedUser(String userName);

	List<User> findAll();

	// check user is exist in database
	Optional<User> findByUserName(String userName);

	// when create data, username has exists in database?
	Boolean existsByUserName(String userName);

	// when create data, email has exists in database?
	Boolean existByEmail(String email);

	// Create data
	User save(User user);

//		
	void follow(String userName) throws ResponseMessage;

	void unfollow(String userName) throws ResponseMessage;
//
//	void createPasswordResetTokenForUser(User user, String token);
//
//	ResponseMessage resetPassword(HttpServletRequest request, String email);
//
//	String validatePasswordResetToken(long id, String token, String email) throws ResponseMessage;
//
//	void forgotPassword(String password) throws ResponseMessage;
//
//	User getUserByEmail(String email) throws ResponseMessage;

}
