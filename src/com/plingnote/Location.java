package com.plingnote;

public class Location {
	private Double longitude;
	private Double latitude;

	/**
	 * 
	 * @param lng longitude of this Location
	 * @param lat latitude of this Location
	 */
	public Location(Double lng, Double lat){
		this.longitude = lng;
		this.latitude = lat;
	}

	/**
	 * 
	 * @return longitude of this Location
	 */
	public Double getLongitude(){
		return this.longitude;
	}

	/**
	 * 
	 * @return latitude of this Location
	 */
	public Double getLatitude(){
		return this.latitude;
	}
}
