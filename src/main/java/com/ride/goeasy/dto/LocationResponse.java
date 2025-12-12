package com.ride.goeasy.dto;

public class LocationResponse {
	 private String lat;
	    private String lon;
	    private String type;

	    public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getLat() {
	        return lat;
	    }
	    public void setLat(String lat) {
	        this.lat = lat;
	    }
	    public String getLon() {
	        return lon;
	    }
	    public void setLon(String lon) {
	        this.lon = lon;
	    }

}
