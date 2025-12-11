package com.ride.goeasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ride.goeasy.entity.Driver;
import com.ride.goeasy.response.ResponseStructure;
import com.ride.goeasy.service.DriverService;

@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	DriverService driverService;

	// to perform save operation

	@PostMapping("/save")
	public ResponseStructure<Driver> saveDriverWithVehicle(@RequestBody Driver driver) {
		return driverService.saveDriverWithVehicle(driver);
	}

	// find operation
	@GetMapping("/find")
	public ResponseStructure<Driver> find(@RequestParam int id) {
		return driverService.find(id);
	}

  
//	Delete Driver by id

	@DeleteMapping("/delete")
	public ResponseStructure<Driver> delete(@RequestParam int id) {
		return driverService.deleteDriverById(id);
	}

//	@PutMapping("/update")
	public ResponseStructure<Driver> updateDriver(@RequestParam int id, @RequestBody Driver newData) {

		return driverService.updateDriver(id, newData);
	}

}
