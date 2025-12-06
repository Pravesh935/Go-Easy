package com.ride.goeasy.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.ride.goeasy.repository.BookingRepo;
import com.ride.goeasy.repository.CustomerRepo;
import com.ride.goeasy.repository.DriverRepo;

@Service
public class BookingService {

	    @Autowired
	    private BookingRepo bookingRepo;

	    @Autowired
	    private CustomerRepo customerRepo;

	    @Autowired
	    private DriverRepo driverRepo;

	   
	  
}
