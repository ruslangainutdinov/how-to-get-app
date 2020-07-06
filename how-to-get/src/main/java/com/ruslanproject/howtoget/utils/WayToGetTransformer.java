package com.ruslanproject.howtoget.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ruslanproject.howtoget.entities.WayToGet;

/**
 * Class is responsible for transforming WayToGet object to its implementations
 * Should be refactored in case of ANY field was added 
 * to basic WayToGet entity(adding corresponding new setter code)
 * 
 * @author Ruslan Gainutdinov
 *
 */
@Component
public class WayToGetTransformer<T extends WayToGet> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WayToGetTransformer.class);
	
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
		LOGGER.debug("Object "+way.getId()+", "+way.getCompanyProvider() +". Was transformed into: "+object.getClass().getSimpleName() );
		return object;
	}
}
