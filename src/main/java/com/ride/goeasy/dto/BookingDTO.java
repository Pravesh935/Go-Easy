package com.ride.goeasy.dto;

public class BookingDTO {

	private Long vehicleId;
	private String sourceLocation;
	private String destinationLocation;
	private Double fare;
	private Double distanceTravelled;
	private String estimatedTime;

	// ---------- Constructors ----------
	public BookingDTO() {
	}

	public BookingDTO(Long vehicleId, String sourceLocation, String destinationLocation, Double fare,
			Double distanceTravelled, String estimatedTime) {
		this.vehicleId = vehicleId;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.fare = fare;
		this.distanceTravelled = distanceTravelled;
		this.estimatedTime = estimatedTime;
	}

	// ---------- Getters & Setters ----------
	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(Double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
}
