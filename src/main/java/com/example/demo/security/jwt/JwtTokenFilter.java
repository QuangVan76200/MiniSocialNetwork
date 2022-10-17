package com.example.demo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws IOException, ServletException {
			try {
				String token = getJwt(request);
				if(token != null && jwtProvider.vaildationToken(token)) {
					String userName = jwtProvider.getUserNameFromToken(token);
					UserDetails userDetail = userDetailsService.loadUserByUsername(userName);
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource());
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
				
			} catch (Exception e) {
				logger.error("Can't set user authentication -> Message:{}", e);
			}
			filterChain.doFilter(request, response);
	}
	private String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			return authHeader.replace("Bearer", "");
		}
		return null;
	}
}
