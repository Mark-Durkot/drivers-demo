package com.md.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.md.security.authority.AppUserRole;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

public class AppUser implements UserDetails {

	@Id
	private final String username;
	
	@Setter
	private String password;
	
	@Column(name = "birth_date")
	@Getter
	private String birthDate;
	
	@Column(name = "user_role")
	@Getter
	private String userRole;
	
	
	public AppUser(String username, String password, String birthDate, String userRole) {
		this.username = username;
		this.password = password;
		this.birthDate = birthDate;
		this.userRole = userRole;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AppUserRole.valueOf(userRole).getGrantedAuthorities();
	}

	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked() { return true; }

	@Override
	public boolean isCredentialsNonExpired() { return true; }

	@Override
	public boolean isEnabled() { return true; }
	
}
