package com.ride.goeasy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import com.ride.goeasy.dto.BookingHistoryDTO;
import com.ride.goeasy.dto.PaymentByCashDTO;
import com.ride.goeasy.dto.PaymentByUpiDTO;
import com.ride.goeasy.dto.RideDetailsDTO;
import com.ride.goeasy.entity.Booking;
import com.ride.goeasy.entity.Customer;
import com.ride.goeasy.entity.Driver;
import com.ride.goeasy.entity.Payment;
import com.ride.goeasy.entity.Vehicle;
import com.ride.goeasy.enums.BookingStatus;
import com.ride.goeasy.exception.BookingNotFoundException;
import com.ride.goeasy.exception.DriverNotFoundException;
import com.ride.goeasy.repository.BookingRepo;
import com.ride.goeasy.repository.CustomerRepo;
import com.ride.goeasy.repository.DriverRepo;
import com.ride.goeasy.repository.PaymentRepo;
import com.ride.goeasy.repository.VehicleRepo;
import com.ride.goeasy.response.ResponseStructure;


@Service
public class DriverService {

	@Autowired
	DriverRepo driverRepo;
	@Autowired
	BookingRepo bookingRepo;
//	Save Driver method
	@Autowired
	PaymentRepo paymentRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	VehicleRepo vehicleRepo;
	@Autowired
	BookingService bs;

	public ResponseStructure<Driver> saveDriverWithVehicle(Driver driver) {

		Driver savedDriver = driverRepo.save(driver);

		ResponseStructure<Driver> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Driver Saved Successfully");
		rs.setData(savedDriver);

		return rs;
	}

//	Find Diver By ID
	public ResponseStructure<Driver> find(int id) {
		Driver findDriver = driverRepo.findById(id).orElseThrow(() -> new DriverNotFoundException("Driver Not Found"));

		ResponseStructure<Driver> rs = new ResponseStructure<Driver>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Driver Found Successfully");
		rs.setData(findDriver);

		return rs;

	}

//	Delete Driver by Id
	public ResponseStructure<Driver> deleteDriverById(int id) {
		// Step 1: Find driver
		Driver driver = driverRepo.findById(id)
				.orElseThrow(() -> new DriverNotFoundException("Driver Not Found With ID: " + id));

		// Step 2: Delete driver
		driverRepo.delete(driver);

		// Step 3: Response
		ResponseStructure<Driver> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Driver Deleted Successfully");
		rs.setData(driver);

		return rs;

	}

//	update driver
	public ResponseStructure<Driver> updateDriver(int id, Driver newData) {

		// Step 1️ Find old data
		Driver oldData = driverRepo.findById(id)
				.orElseThrow(() -> new DriverNotFoundException("Driver Not Found with ID: " + id));

		// Step 2️ Update fields
		oldData.setDname(newData.getDname());
		oldData.setLicNo(newData.getLicNo());
		oldData.setUpiId(newData.getUpiId());
		oldData.setDstatus(newData.getDstatus());
		oldData.setAge(newData.getAge());
		oldData.setMobNo(newData.getMobNo());
		oldData.setGender(newData.getGender());
		oldData.setMailId(newData.getMailId());

		// Step 3 Save updated data
		Driver updatedDriver = driverRepo.save(oldData);

		// Step 4️ Response structure
		ResponseStructure<Driver> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Driver Updated Successfully");
		rs.setData(updatedDriver);

		return rs;
	}
	
	
	private byte[] generateQrCode(String upiString) {
	    try {
	        String qrUrl =
	            "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + upiString;

	        URL url = new URL(qrUrl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        InputStream is = conn.getInputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = is.read(buffer)) != -1) {
	            baos.write(buffer, 0, read);
	        }

	        return baos.toByteArray();
	    } catch (Exception e) {
	        throw new RuntimeException("QR generation failed");
	    }
	}

	
	//payment by cash

	public ResponseStructure<PaymentByCashDTO> confirmPaymnetByCash(int bookingId ,String paymentType) {
		return confirmPay(bookingId, paymentType);

	}
	
	//payment confirmation method 

	private ResponseStructure<PaymentByCashDTO> confirmPay(int bookingId, String paymentType) {
		Booking b = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with id:" + bookingId));
		b.setBookingStatus(BookingStatus.COMPLETED);

		Customer c = b.getCustomer();
		c.setActiveBookingFlag(false);

		Vehicle v = b.getVehicle();
		v.setAvlStatus("AVAILABLE");
		b.setActiveBookingFlag(false);

		Payment p = b.getPayment();
		p.setVehicle(v);
		p.setCustomer(c);
		p.setBooking(b);
		p.setPaymentStatus("PAID");
		p.setPaymentType(paymentType);
		p.setAmount(b.getFare());

		b.setPayment(p);
		paymentRepo.save(p);
		bookingRepo.save(b);
		customerRepo.save(c);
		vehicleRepo.save(v);

		PaymentByCashDTO pdto = new PaymentByCashDTO();
		pdto.setBookingId(b.getId());
		pdto.setCustomerId(c.getId());
		pdto.setDriverId(v.getId());
		pdto.setAmountPaid(b.getFare());
		pdto.setPaymentType(paymentType);
		pdto.setPaymentStatus("PAID");

		ResponseStructure<PaymentByCashDTO> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Ride completed ->Amount paid");
		rs.setData(pdto);

		return rs;
	}

	
	
	
	
	
	
	
  
	public ResponseStructure<BookingHistoryDTO> getDriverBookingHistory(long mobNo) {
		
		Driver d=	driverRepo.findByMobNo(mobNo).orElseThrow(() -> new DriverNotFoundException("Driver Not Found with Mobile: " + mobNo));
        List<Booking> blist= d.getDblist();
        if (blist == null || blist.isEmpty()) {
            throw new BookingNotFoundException("No bookings found for driver");
        }
       return   bs.getBookingHistory(blist);
			
			 
		}

	public ResponseStructure<RideDetailsDTO> getDriverActiveBooking(long mobNo) {
		Driver d=	driverRepo.findByMobNo(mobNo).orElseThrow(() -> new DriverNotFoundException("Driver Not Found with Mobile: " + mobNo));
		 List<Booking> blist= d.getDblist();
	     return bs.activeBookingHistory(blist);
	}
	
	
	
	
	
	


	public ResponseStructure<PaymentByUpiDTO> confirmPaymentByUPI(int bookingId) {

	    Booking b = bookingRepo.findById(bookingId)
	        .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

	    Vehicle v = b.getVehicle();
	    Driver d = v.getDriver();
	    Customer c = b.getCustomer();

	    double fare = b.getFare();

	    // 🔹 Create UPI intent
	    String upiString =
	        "upi://pay?pa=" + d.getUpiId()
	        + "&pn=" + d.getDname()
	        + "&am=" + fare
	        + "&cu=INR";

	    // 🔹 Generate QR
	    byte[] qrBytes = generateQrCode(upiString);

	    // 🔹 Payment pending
	    Payment p = b.getPayment();
	    if ("PAID".equalsIgnoreCase(p.getPaymentStatus())) {
	        throw new RuntimeException("Payment already completed");
	    }
	    p.setPaymentType("UPI");
	    p.setPaymentStatus("PENDING");
	    p.setAmount(fare);

	    paymentRepo.save(p);

	    // 🔹 DTO
	    PaymentByUpiDTO dto = new PaymentByUpiDTO();
	    dto.setBookingId(b.getId());
	    dto.setCustomerId(c.getId());
	    dto.setDriverId(d.getId());
	    dto.setAmount(fare);
	    dto.setPaymentType("UPI");
	    dto.setPaymentStatus("PENDING");
	    dto.setQr(qrBytes);

	    ResponseStructure<PaymentByUpiDTO> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("Scan QR to pay via UPI");
	    rs.setData(dto);

	    return rs;
	}
	
	
	
	public ResponseStructure<PaymentByUpiDTO> confirmUpiPaymentSuccess(int bookingId) {

	    Booking b = bookingRepo.findById(bookingId)
	        .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

	    b.setBookingStatus(BookingStatus.COMPLETED);

	    Customer c = b.getCustomer();
	    c.setActiveBookingFlag(false);

	    Vehicle v = b.getVehicle();
	    v.setAvlStatus("AVAILABLE");

	    Payment p = b.getPayment();
	    p.setPaymentStatus("PAID");
	    p.setPaymentType("UPI");
	    p.setAmount(b.getFare());

	    paymentRepo.save(p);
	    bookingRepo.save(b);
	    customerRepo.save(c);
	    vehicleRepo.save(v);

	    PaymentByUpiDTO dto = new PaymentByUpiDTO();
	    dto.setBookingId(b.getId());
	    dto.setCustomerId(c.getId());
	    dto.setDriverId(v.getDriver().getId());
	    dto.setAmount(b.getFare());
	    dto.setPaymentType("UPI");
	    dto.setPaymentStatus("PAID");

	    ResponseStructure<PaymentByUpiDTO> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("UPI Payment Successful");
	    rs.setData(dto);

	    return rs;
	}



}
