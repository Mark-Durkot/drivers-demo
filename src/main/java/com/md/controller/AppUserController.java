package com.md.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.md.entity.AppUser;
import com.md.repository.AppUserRepository;
import com.md.security.jwt.JwtTokenGenerator;
import com.md.security.jwt.JwtTokenVerifier;
import com.md.service.AppUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@Slf4j
public class AppUserController {

	private final AppUserService userService;
	
	@Autowired
	public AppUserController(AppUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ROOT')")
	public Iterable<AppUser> allUsers() {
		return userService.getAllUsers(); 
	}
	
	@GetMapping("/token/refresh")
	public void tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader("Authorization");
		
		if (token == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No token provided");
		}
		
		try {
			Authentication authentication = JwtTokenVerifier.verifyToken(token);
			AppUser user = userService.loadUserByUsername(authentication.getName());
			
			String access_token = JwtTokenGenerator.generateToken(user, request, 2 * 60 * 1000);
			String refresh_token = JwtTokenGenerator.generateToken(user, request, 10 * 60 * 1000);
			
			response.addHeader("access_token", access_token);
			response.addHeader("refresh_token", refresh_token);
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
	}
	
	@GetMapping("/{username}")
	@PreAuthorize("hasRole('ROLE_ROOT')")
	public AppUser getUserByUsername(@PathVariable String username) {
		try {
			return userService.loadUserByUsername(username);
		} catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
