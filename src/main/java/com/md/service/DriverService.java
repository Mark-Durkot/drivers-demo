package com.md.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.entity.Driver;
import com.md.repository.DriverRepository;

@Service
public class DriverService {
	
	private DriverRepository driverRepo;
	
	@Autowired
	public DriverService(DriverRepository driverRepo) {
		this.driverRepo = driverRepo;
	}
	
	public Iterable<Driver> getAllDrivers() {
		return driverRepo.findAll();
	}
	
	public Optional<Driver> getDriverById(Long id) {
		return driverRepo.findById(id);
	}
	
	public Driver saveDriver(Driver driver) {
		return driverRepo.save(driver);
	}
	
	public int updateDriverById(Long id, String name) {
		return driverRepo.updateNameById(id, name);
	}
	
	public void deleteDriverById(Long id) {
		driverRepo.deleteById(id);
	}
	
}
