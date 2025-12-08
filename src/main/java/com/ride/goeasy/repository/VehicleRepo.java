package com.ride.goeasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ride.goeasy.entity.Vehicle;

public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {
	
	
	// Fetch all vehicles that are Available
	@Query("SELECT v FROM Vehicle v WHERE v.avlStatus = 'Available'")
	List<Vehicle> findAvailableVehicles();
	
	

	// Find by city
	@Query("SELECT v FROM Vehicle v WHERE v.avlStatus = 'Available' AND v.city = :city")
	List<Vehicle> findAvailableVehiclesInCity(String city);

}
