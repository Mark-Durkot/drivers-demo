package com.md.repository;

import org.springframework.data.repository.CrudRepository;

import com.md.entity.DriverLicense;

public interface DriverLicenseRepository extends CrudRepository<DriverLicense, Long> {

}
