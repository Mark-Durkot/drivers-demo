package com.md.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.md.entity.AppUser;
import com.md.repository.AppUserRepository;

@Service
public class RegistrationService {

	private final AppUserRepository appUserRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public RegistrationService(AppUserRepository appUserRepo, PasswordEncoder passwordEncoder) {
		this.appUserRepo = appUserRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public AppUser register(AppUser user) {
		
		boolean userExists = appUserRepo.findByUsername(user.getUsername()).isPresent();
		
		if (userExists) {
			throw new RuntimeException("User exists");
		}
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		return appUserRepo.save(user);
	}
}
