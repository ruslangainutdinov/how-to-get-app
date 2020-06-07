package com.ruslanproject.howtoget.enities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flights")
public class Flight extends WayToGet {

	
	
	public Flight(String id, String companyProvider, double price, String departureDate, String arrivalDate,
			String locationFrom, String locationTo) {
		super(id, companyProvider, price, departureDate, arrivalDate, locationFrom, locationTo);
	}
	public Flight() {}
	
	@Override
	public String toString() {
		return "Flight =" + super.toString() + "]";
	}

	
}
