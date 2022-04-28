package com.md.repository;

import org.springframework.data.repository.CrudRepository;

import com.md.entity.VehicleClass;

public interface VehicleClassRepository extends CrudRepository<VehicleClass, Short> {
	
}
