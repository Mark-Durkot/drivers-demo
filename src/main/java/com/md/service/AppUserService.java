package com.md.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.md.entity.AppUser;
import com.md.repository.AppUserRepository;

@Service
public class AppUserService implements UserDetailsService {

	@Autowired
	private final AppUserRepository userRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AppUserService(AppUserRepository userRepo,
						  PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Iterable<AppUser> getAllUsers() {
		return userRepo.findAll();
	}
	
	@Override
	public AppUser loadUserByUsername(String username) {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException(String.format("User with username: %s not found",
																	  username)));
	}
	
	public boolean isPresent(AppUser user) {
		return userRepo.findByUsername(user.getUsername()).isPresent();
	}
	
	public void deleteUserByUsername(String username) {
		userRepo.deleteById(username);
	}
	
}
