package com.example.demo.controller;

import java.lang.module.ResolutionException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.AssertingParty.Verification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.IUserDao;
import com.example.demo.dto.request.SignInForm;
import com.example.demo.dto.request.SignUpForm;
import com.example.demo.dto.respone.JwtResponse;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Response;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
import com.example.demo.entity.User;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.userprical.UserPrinciple;
import com.example.demo.serviceImpl.RoleServiceImpl;
import com.example.demo.serviceImpl.UserServiceImpl;


@RequestMapping("/api/auth")
@RestController
public class AuthController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private RoleServiceImpl roleServiceImpl;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	IUserDao userDao;
	
	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) {
		if (userServiceImpl.existsByUserName(signUpForm.getUserName())) {
			return new ResponseEntity<>(new ResponseMessage("The Username is exists! please try another username"), HttpStatus.OK);
		}
		if(userServiceImpl.existByEmail(signUpForm.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("The Email is exists! please try another Email"), HttpStatus.OK);
		}
		
		
		
				
		User user = new User(signUpForm.getFullName() ,signUpForm.getUserName(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
		user.setNumbOfFollowers(0);
		user.setNumbOfFollowing(0);
		Set<String> strRoles = signUpForm.getRoles();
		Set<Role> setRoles = new HashSet<>();
		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role roleAdmin = roleServiceImpl.findByRoleName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role Not Found"));
				setRoles.add(roleAdmin);
				break;
			case "MANAGER":
				Role roleManager = roleServiceImpl.findByRoleName(RoleName.MANAGER).orElseThrow(()-> new RuntimeException("Role Not found"));
				setRoles.add(roleManager);
				break;
			default:
				Role userRole = roleServiceImpl.findByRoleName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role Not Found"));
				setRoles.add(userRole);
				break;
			}
		});
		user.setRoles(setRoles);
//		
//		if(!userServiceImpl.isCorrectConfirmPassword(user)) {
//		    return new ResponseEntity<>(new ResponseMessage("You need to enter the correct verification code"), HttpStatus.BAD_REQUEST);
//		}
		
//		user.setPassword(passwordEncoder.encode(user.getConfirmPassword()));
		userServiceImpl.save(user);
		
		System.out.println(user);
		System.out.println(user.getFullName());
		return new ResponseEntity<>(new ResponseMessage("Create success fully"), HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) throws Exception{
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(signInForm.getUserName(), signInForm.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String token = jwtProvider.createToken(authentication);
	        System.out.println(token);
	        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
	        return  ResponseEntity.ok(new JwtResponse(token, userPrincipal.getFullName(), userPrincipal.getAuthorities()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Failed", e.getMessage(), null));
        }
		
	}
	

}

