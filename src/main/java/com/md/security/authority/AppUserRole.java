package com.md.security.authority;

import static com.md.security.authority.AppUserPermission.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


public enum AppUserRole 
{
	ROOT(list(DRIVER_READ, DRIVER_WRITE, LICENSE_READ, LICENSE_WRITE)),
	
	ADMIN(list(DRIVER_READ, LICENSE_READ)),
	
	DRIVER(list(DRIVER_READ, LICENSE_READ));
	
	
	private List<AppUserPermission> permissions;
	
	
	AppUserRole(List<AppUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public List<AppUserPermission> getPermissions() {
		return permissions;
	}
	
	public List<SimpleGrantedAuthority> getGrantedAuthorities() {
		List<SimpleGrantedAuthority> permissions = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}
	
	private static List<AppUserPermission> list(AppUserPermission ...permissions) {
		
		List<AppUserPermission> set = new ArrayList<>();
		
		for (AppUserPermission p : permissions) {
			set.add(p);
		}
		
		return set;
	}
}
