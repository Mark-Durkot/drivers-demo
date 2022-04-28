package com.md.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.md.entity.Driver;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

	@Modifying
	@Query("UPDATE Driver d SET name = :name WHERE id = :id")
	public int updateNameById(@Param("id") Long id, @Param("name") String name);
	
}
