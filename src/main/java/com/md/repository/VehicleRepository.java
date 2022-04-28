package com.md.repository;

import org.springframework.data.repository.CrudRepository;

import com.md.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {	
	
}
