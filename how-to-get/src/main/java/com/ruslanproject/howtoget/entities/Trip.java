package com.ruslanproject.howtoget.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ruslanproject.howtoget.validators.FieldsDoNotMatch;

/**
 * Entity class for Trip
 * 
 * @author Ruslan Gainutdinov
 *
 */

@FieldsDoNotMatch(first = "locationFrom", second = "locationTo",message="Departure location and arrival location should not match")
public class Trip {

	@NotNull
	@Size(min=1, message="Please choose departure location")
	private String locationFrom;
	
	@NotNull
	@Size(min=1, message="Please choose arrival location")
	private String locationTo;

	private String departureDate;
	private LocalDateTime timestamp;
	
	private Set<String> types= new HashSet<>();
	
	public enum TypeOfWay{
		BUS,
		FLIGHT
	}
	public Trip() {
		
	}
	
	public Trip(String locationFrom, String locationTo, String departureDate) {
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
		this.departureDate = departureDate;
	}

	public String getLocationFrom() {
		return locationFrom;
	}
	public void setLocationFrom(String locationFrom) {
		this.locationFrom = locationFrom;
	}
	public String getLocationTo() {
		return locationTo;
	}
	public void setLocationTo(String locationTo) {
		this.locationTo = locationTo;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	@Override
	public String toString() {
		return "From: " + locationFrom + ", to: " + locationTo + ", departure date: " + departureDate;
	}

	

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Set<String> getTypes() {
		return types;
	}

	public void setTypes(Set<String> types) {
		this.types = types;
	}
	
	
}
