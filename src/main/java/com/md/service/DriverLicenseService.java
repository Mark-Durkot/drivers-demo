package com.md.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.entity.DriverLicense;
import com.md.entity.VehicleClass;
import com.md.repository.DriverLicenseRepository;

@Service
public class DriverLicenseService {

	private DriverLicenseRepository licenseRepo;
	
	@Autowired
	public DriverLicenseService(DriverLicenseRepository licenseRepo) {
		this.licenseRepo = licenseRepo;
	}
	
	public Iterable<DriverLicense> getAllLicenses() {
		return licenseRepo.findAll();
	}
	
	public DriverLicense save(DriverLicense license) {
		return licenseRepo.save(license);
	}
	
	public Optional<DriverLicense> getLicenseById(Long id) {
		return licenseRepo.findById(id);
	}
	
	public List<VehicleClass> getAllVehicleClasses(Long id) {
		DriverLicense license = licenseRepo.findById(id).get();
		
		if (license != null) {
			return license.getClasses();
		}
		
		return null;
	}
}
