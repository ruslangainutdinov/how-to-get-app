package com.ruslanproject.howtoget.services;

import java.util.List;

import com.ruslanproject.howtoget.entities.Flight;
import com.ruslanproject.howtoget.entities.Trip;
import com.ruslanproject.howtoget.entities.WayToGet;
/**
 * Base interface for WayToGetService implementations
 * 
 * @author Ruslan Gainutdinov
 *
 */
public interface WayToGetService {
	
	public List<? extends WayToGet> findAllByTrip(Trip trip);
	
}
