package com.cabapplication.user.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	//secret key can be modified in application.yml
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
//		Claims claims = extractAllClaims(token);
//		claims.getExpiration();
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public Boolean isTokenExpired(String token) {
		//true if token expired
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String employeeMail) {
		
		//to add any other claims like authority
		Map<String, Object> claims = new HashMap<>();
//		claims.put("ROLE", userDetails.getAuthorities());
		
		return createToken(claims, employeeMail);
	}
	
	//generate jwt token
	private String createToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
																		//10 secs
//					.setExpiration(new Date(System.currentTimeMillis() + 6000))
																		//1 hour
					.setExpiration(new Date(System.currentTimeMillis() + 3600000))
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean validateToken(String token, String employeeMail) {
		final String userName = extractUserName(token);
		return userName.equals(employeeMail) && !isTokenExpired(token);
	}
	
}
