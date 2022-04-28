package com.md.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.md.entity.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, String> {
	
	@Query("SELECT u FROM AppUser u WHERE u.username = ?1")
	Optional<AppUser> findByUsername(String username);
	
}
