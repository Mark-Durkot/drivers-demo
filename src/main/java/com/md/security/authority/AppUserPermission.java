package com.md.security.authority;

public enum AppUserPermission {

	DRIVER_READ("driver:read"),
	DRIVER_WRITE("driver:write"),
	LICENSE_READ("license:read"),
	LICENSE_WRITE("license:write");
	
	private final String permission;
	
	AppUserPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}
