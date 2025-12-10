package com.ride.goeasy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {

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

}
