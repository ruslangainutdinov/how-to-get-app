package com.ruslanproject.howtoget.utils;

import org.springframework.stereotype.Component;

import com.ruslanproject.howtoget.enities.WayToGet;

@Component
public class WayToGetTransformer<T extends WayToGet> {

	
	
	public T transform(WayToGet way,T object){
		object.setArrivalDate(way.getArrivalDate());
		object.setCompanyProvider(way.getCompanyProvider());
		object.setDepartureDate(way.getDepartureDate());
		object.setId(way.getId());
		object.setLocationFrom(way.getLocationFrom());
		object.setLocationTo(way.getLocationTo());
		object.setPrice(way.getPrice());
		object.setUfn(way.getUfn());
		object.setTicketsAvailable(way.getTicketsAvailable());
		return object;
	}
}
