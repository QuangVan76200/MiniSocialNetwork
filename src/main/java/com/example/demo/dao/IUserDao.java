package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.User;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {
	// check user is exist in database
	Optional<User> findByUserName(String userName); 
	
	// check create data, username has exists in database?
	Boolean existsByUserName(String userName); 

	// check create data, email has exists in database?
	Boolean existsByEmail(String email); 
	
	// get data, email from database
	Optional<User> findByEmail(String email)throws ResponseMessage;
	
//	Optional<PasswordResetToken> findByToken(String token);
	
	public User findByResetPasswordToken(String token);
	
//	@Modifying
//	@Query(value = "select U from users U INNER JOIN followers F ON U.user_id = F.user_id where U.user_name= :userName")
//	public List<User> getAllFollower(@Param("userName") String userName);
	
	
	
	
	
	

}
