package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.IUserDao;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/homepage")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	IUserDao userDao;
	
	@GetMapping("/")
	public Iterable<User> getAllUser(){
		return userServiceImpl.findAll();
	}
	
	@PostMapping("/follow/{userName}/")
	public ResponseEntity<Response> followUser(@PathVariable String userName){
		try {
			userServiceImpl.follow(userName);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Successful","",""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Failed",e.getMessage(),""));
		}
		
	}
	
	@PostMapping("/unfollow/{userName}/")
	public ResponseEntity<Response> unFollowUser(@PathVariable String userName){
		try {
			userServiceImpl.unfollow(userName);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Successful","",""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Failed",e.getMessage(),""));
		}
		
	}
	
	

}

