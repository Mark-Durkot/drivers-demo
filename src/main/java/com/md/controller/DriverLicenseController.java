package com.md.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.md.entity.DriverLicense;
import com.md.service.DriverLicenseService;

@RestController
@RequestMapping(path = "/licenses", produces = "application/json")
public class DriverLicenseController {

	@Autowired
	private DriverLicenseService driverLicenseService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN')")
	public ResponseEntity<Iterable<DriverLicense>> getAllLicenses() {
		return new ResponseEntity<>(driverLicenseService.getAllLicenses(), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('license:write')")
	public ResponseEntity<DriverLicense> saveLicense(@RequestBody DriverLicense license) {
		license = driverLicenseService.save(license);
		
		if (license == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(license, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('license:read')")
	public DriverLicense getLicenseById(@PathVariable Long id) {
		return driverLicenseService.getLicenseById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error"));
	}
	
}
