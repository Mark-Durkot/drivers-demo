package com.md.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "driver_licenses")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property  = "id")
public class DriverLicense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@OneToOne
	@JoinColumn(name = "driver_id")
	@JsonIgnore
	private Driver driver;
	
	@ManyToMany
	@JoinTable(name = "license_vehicle_classes",
			joinColumns = @JoinColumn(name = "vehicle_class_id"),
			inverseJoinColumns = @JoinColumn(name = "driver_license_id"))
	private List<VehicleClass> classes;
}
