package com.ride.goeasy.entity;

import jakarta.persistence.Entity;
<<<<<<< HEAD
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
=======
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
>>>>>>> c2c952ec89b1d7ab1db862988a3f4d4531dd7dc9
import jakarta.persistence.OneToOne;

@Entity
public class Payment {
<<<<<<< HEAD

	@Id
	private String paymentId;

	// --------------------- Relationships ---------------------

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;

	@OneToOne
	@JoinColumn(name = "booking_id", nullable = false)
	private Booking booking;

	// --------------------- Payment Details ---------------------

	private double amount;

	private String paymentType; // UPI, CARD, CASH, WALLET

	private String currency = "INR";

	// --------------------- Getters & Setters ---------------------

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

=======
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @ManyToOne
  private Customer customer;
  
  @ManyToOne
  private Vehicle vehicle;
  @OneToOne
  private Booking booking;
  
  private double amount;
  private String paymentType;
  
  
  public Payment(Customer customer, Vehicle vehicle, Booking booking, double amount, String paymentType) {
	super();
	this.customer = customer;
	this.vehicle = vehicle;
	this.booking = booking;
	this.amount = amount;
	this.paymentType = paymentType;
	
	
  }


  public Payment() {
	super();
	// TODO Auto-generated constructor stub
  }


  public int getId() {
	return id;
  }


 


  public Customer getCustomer() {
	return customer;
  }


  public void setCustomer(Customer customer) {
	this.customer = customer;
  }


  public Vehicle getVehicle() {
	return vehicle;
  }


  public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
  }


  public Booking getBooking() {
	return booking;
  }


  public void setBooking(Booking booking) {
	this.booking = booking;
  }


  public double getAmount() {
	return amount;
  }


  public void setAmount(double amount) {
	this.amount = amount;
  }


  public String getPaymentType() {
	return paymentType;
  }


  public void setPaymentType(String paymentType) {
	this.paymentType = paymentType;
  }
  
  
  
>>>>>>> c2c952ec89b1d7ab1db862988a3f4d4531dd7dc9
}
