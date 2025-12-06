package com.ride.goeasy.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ride.goeasy.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	  @Autowired
	    private BookingService bookingService;
	  
	 
	  

	    
}
