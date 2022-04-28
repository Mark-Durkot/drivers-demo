package com.md.security.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtTokenGenerator {

	public static String generateToken(UserDetails user, HttpServletRequest request, int expirationMiliseconds) {
		
		String token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationMiliseconds))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
																  .collect(Collectors.toList()))
				.sign(Algorithm.HMAC256("secret".getBytes()));
		
		return token;
	}
	
}
