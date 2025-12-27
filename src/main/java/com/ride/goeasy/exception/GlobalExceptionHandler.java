package com.ride.goeasy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.ride.goeasy.entity.Booking;
import com.ride.goeasy.entity.Customer;
import com.ride.goeasy.entity.Driver;
import com.ride.goeasy.entity.Payment;
import com.ride.goeasy.entity.Vehicle;
import com.ride.goeasy.response.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

//	customer Exception

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseStructure<Customer> CustomerNotFoundException(CustomerNotFoundException ex) {
		ResponseStructure<Customer> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage(ex.getMessage());
		rs.setData(null);

		return rs;

	}

//	Driver Exception

	@ExceptionHandler(DriverNotFoundException.class)
	public ResponseStructure<Driver> DriverNotFoundException(DriverNotFoundException ex) {

		ResponseStructure<Driver> rs = new ResponseStructure();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage(ex.getMessage());
		rs.setData(null);
		return rs;

	}

//	Vehicle Exception
	@ExceptionHandler(VehicleNotFoundException.class)
	public ResponseStructure<Vehicle> VehicleNotFoundException(VehicleNotFoundException ex) {
		ResponseStructure<Vehicle> rs = new ResponseStructure();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage(ex.getMessage());
		rs.setData(null);
		return rs;

	}

//	Booking Exception
	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseStructure<Booking> BookingNotFoundException(BookingNotFoundException ex) {
		ResponseStructure<Booking> rs = new ResponseStructure();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage(ex.getMessage());
		rs.setData(null);

		return rs;

	}

	// Invalid Location Exception
	  @ExceptionHandler(InvalidLocationException.class)
	    public ResponseEntity<String> handleInvalidLocation(InvalidLocationException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }

	    @ExceptionHandler(HttpClientErrorException.class)
	    public ResponseEntity<String> handleHttpClientError(HttpClientErrorException ex) {
	        // Handles LocationIQ errors like invalid API key or malformed request
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Something went wrong: " + ex.getMessage());
	    }
	 
	
	// Payment Exception
	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseStructure<Payment> handlePaymentNotFound(PaymentNotFoundException ex) {

	    ResponseStructure<Payment> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.NOT_FOUND.value());
	    rs.setMessage(ex.getMessage());
	    rs.setData(null);

	    return rs;
	}
	
	//MobileAlreadyRegisteredException
	

    @ExceptionHandler(MobileAlreadyRegisteredException.class)
    public ResponseEntity<ResponseStructure<String>> handleMobileAlreadyRegistered(
            MobileAlreadyRegisteredException ex) {

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage(ex.getMessage());
        rs.setData("FAILED");

        return ResponseEntity.badRequest().body(rs);
    }

	
	
}
 
		 

