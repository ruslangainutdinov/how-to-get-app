package com.ruslanproject.howtoget.services;

import java.util.List;

import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;
/**
 * Base interface for WayToGetService implementations
 * 
 * @author Ruslan Gainutdinov
 *
 */
public interface WayToGetService {
	
	public List<? extends WayToGet> findAllByTrip(Trip trip);
	
}
