package com.md.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.md.entity.AppUser;
import com.md.security.jwt.JwtTokenGenerator;
import com.md.service.RegistrationService;

@RestController
@RequestMapping(path = "/signup")
public class RegistrationController {

	private final RegistrationService registrationService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@PostMapping
	public void register(@RequestBody AppUser user, HttpServletRequest request, HttpServletResponse response) {
		try {
			user = registrationService.register(user);
		} catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
		
		String access_token = JwtTokenGenerator.generateToken(user, request, 2 * 60 * 1000);
		String refresh_token = JwtTokenGenerator.generateToken(user, request, 10 * 60 * 1000);
		
		response.setHeader("access_token", access_token);
		response.setHeader("refresh_token", refresh_token);
	}
	
}
