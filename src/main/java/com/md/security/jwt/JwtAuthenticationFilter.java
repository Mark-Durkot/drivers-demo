package com.md.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import com.md.entity.AppUser;

import lombok.extern.slf4j.Slf4j;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	@Autowired
	public JwtAuthenticationFilter(AuthenticationManager authManager) {
		this.authenticationManager = authManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
		throws AuthenticationException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || password == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ullaalala");
		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authentication);
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
										 FilterChain chain, Authentication authentication) 
												 throws IOException, ServletException {
		
		UserDetails user = (UserDetails)authentication.getPrincipal();
		
		String accessToken  = JwtTokenGenerator.generateToken(user, request, 2 * 60 * 1000);
		String refreshToken = JwtTokenGenerator.generateToken(user, request, 10 * 60 * 1000);
		
		response.setHeader("accessToken",  "Bearer " + accessToken);
		response.setHeader("refreshToken", "Bearer " + refreshToken);
	}
	
}
