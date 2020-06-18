package com.ruslanproject.howtoget.enities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for Bus
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Entity
@Table(name="buses")
public class Bus extends WayToGet {
	
	public Bus(String id, String companyProvider, double price, String departureDate, String arrivalDate,
			String locationFrom, String locationTo) {
		super(id, companyProvider, price, departureDate, arrivalDate, locationFrom, locationTo);

	}
	public Bus() {
		
	}
	@Override
	public String toString() {
		return "Bus =" + super.toString() + "]";
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	

}
