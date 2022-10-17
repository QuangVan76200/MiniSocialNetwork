package com.example.demo.security.jwt;

import java.nio.file.attribute.UserPrincipal;
import java.util.Date;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.security.userprical.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	private String jwtSecret = "Thuhoai.76200";
	private int jwtExpiration = 86400;

	public String createToken(Authentication authentication) {
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

	}

	// check token is valid
	public boolean vaildationToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("invalid JWT signature -> Message:{}", e);
		} catch (MalformedJwtException e) {
			logger.error("invalid Token format -> Message:{}", e);
		} catch (ExpiredJwtException e) {
			logger.error("Token is session time out -> Message:{}", e);
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupport JWT Token -> Message:{}", e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is blank -> Message:{}", e);
		}
		return false;
	}

	public String getUserNameFromToken(String token) {
		String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return userName;
	}
}
