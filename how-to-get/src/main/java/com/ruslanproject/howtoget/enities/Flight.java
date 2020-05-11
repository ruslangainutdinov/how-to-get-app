package com.ruslanproject.howtoget.enities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flights")
public class Flight extends WayToGet {

	
	
	public Flight(String id, String companyProvider, double price, String departureDate, String duration,
			String locationFrom, String locationTo) {
		super(id, companyProvider, price, departureDate, duration, locationFrom, locationTo);
		// TODO Auto-generated constructor stub
	}
	public Flight() {}
	
	@Override
	public String toString() {
		return "Flight =" + super.toString() + "]";
	}

	
}
