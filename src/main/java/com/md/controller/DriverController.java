package com.md.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.md.entity.Driver;
import com.md.entity.DriverLicense;
import com.md.entity.Vehicle;
import com.md.service.DriverService;

@RestController
@RequestMapping(path = "/drivers", produces = "application/json")
public class DriverController {
	
	@Autowired
	private DriverService driverService;
	
	private JsonParser jsonParser = new BasicJsonParser();
	
	
	@GetMapping
	public ResponseEntity<Iterable<Driver>> allDrivers() {
		return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Iterable<Driver>> saveDriver(Driver driver) {
		driverService.saveDriver(driver);
		return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Driver> driverBy(@PathVariable Long id) {
		Optional<Driver> driverOpt = driverService.getDriverById(id);
		
		if (driverOpt.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(driverOpt.get(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Iterable<Driver>> updateDriver(@PathVariable Long id, @RequestBody String json) {
		Map<String, Object> map = jsonParser.parseMap(json);
		String driverName = (String)map.get("driverName"); 
		
		driverService.updateDriverById(id, driverName);
		
		return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Iterable<Driver>> deleteDriver(@PathVariable Long id) {
		driverService.deleteDriverById(id);
		return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
	}
	
	@GetMapping("{id}/license")
	public ResponseEntity<DriverLicense> driverLicense(@PathVariable Long id) {
		Optional<Driver> driverOpt = driverService.getDriverById(id);
		
		if (driverOpt.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(driverOpt.get().getDriverLicense(), HttpStatus.OK);
	}
	
	@GetMapping("{id}/vehicles")
	public ResponseEntity<List<Vehicle>> driverVehicles(@PathVariable Long id) {
		Optional<Driver> driverOpt = driverService.getDriverById(id);
		
		if (driverOpt.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(driverOpt.get().getVehicles(), HttpStatus.OK);
	}
	
	@GetMapping("{driverId}/vehicles/{vehicleId}")
	public ResponseEntity<Vehicle> driversVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
		List<Vehicle> vehicles = driverService.getDriverById(driverId).get().getVehicles();
		
		for (Vehicle v : vehicles) {
			if (v.getId() == vehicleId) {
				return new ResponseEntity<>(v, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
