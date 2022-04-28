package com.md.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.entity.Vehicle;
import com.md.repository.VehicleRepository;

@Service
public class VehicleService {

	private VehicleRepository vehicleRepo;
	
	@Autowired
	public VehicleService(VehicleRepository vehicleRepo) {
		this.vehicleRepo = vehicleRepo;
	}
	
	public Iterable<Vehicle> getAllVehicles() {
		return vehicleRepo.findAll();
	}
	
	public Vehicle getVehicleById(Long id) {
		return vehicleRepo.findById(id).get();
	}
	
	public Iterable<Vehicle> save(Vehicle vehicle) {
		vehicleRepo.save(vehicle);
		return vehicleRepo.findAll();
	}
}
