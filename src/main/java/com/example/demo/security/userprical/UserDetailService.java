package com.example.demo.security.userprical;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IUserDao;
import com.example.demo.entity.User;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	IUserDao userDao;

	// Check user is exists on database
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username).orElseThrow(()-> 
		new UsernameNotFoundException("user not found -> username or password "+username));
		System.out.println("username"+username);
		return UserPrinciple.build(user);
	}

}
