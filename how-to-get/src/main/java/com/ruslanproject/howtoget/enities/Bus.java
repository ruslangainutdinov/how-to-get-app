package com.ruslanproject.howtoget.enities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="buses")
public class Bus extends WayToGet {
	
	public Bus(String id, String companyProvider, double price, String departureDate, String duration,
			String locationFrom, String locationTo) {
		super(id, companyProvider, price, departureDate, duration, locationFrom, locationTo);

	}
	public Bus() {
		
	}
	@Override
	public String toString() {
		return "Bus =" + super.toString() + "]";
	}
	
	

}
