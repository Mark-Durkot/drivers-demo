package com.md.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.md.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	private final AppUserRepository appUserRepo;
	
	@Autowired
	public AppUserDetailsService(AppUserRepository appUserRepo) {
		this.appUserRepo = appUserRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
	}

}
