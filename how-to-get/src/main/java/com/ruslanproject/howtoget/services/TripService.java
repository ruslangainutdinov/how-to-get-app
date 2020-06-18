package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;

/**
 * Service class for TripService
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Service
public class TripService {
		
	
	
	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	public List<String> getAllLocations() {
		List<Flight> flights =flightRepository.findAll();
		
		List<String> accumulatorLocations = flights.stream().map(f->f.getLocationFrom()).distinct().collect(Collectors.toList());
		
		List<String> locationsToFlights = flights.stream().map(f->f.getLocationTo()).distinct().filter(b->!accumulatorLocations.contains(b)).collect(Collectors.toList());
		
		accumulatorLocations.addAll(locationsToFlights);
		
		List<Bus> buses = busRepository.findAll();
		
		List<String> locationsFromBuses= buses.stream().map(b->b.getLocationFrom()).distinct().filter(b->!accumulatorLocations.contains(b)).collect(Collectors.toList());
		
		accumulatorLocations.addAll(locationsFromBuses);
		
		List<String> locationsToBuses= buses.stream().map(b->b.getLocationTo()).distinct().filter(b->!accumulatorLocations.contains(b)).collect(Collectors.toList());
	
		accumulatorLocations.addAll(locationsToBuses);
		
		return accumulatorLocations;
	}
	
	
	/*public List<? extends WayToGet> filter(List<? extends WayToGet> ways, Trip trip) {
		System.out.println(ways);
		ways = ways.stream()
				.filter(way -> way.getDepartureDate().substring(0, 10).equals(trip.getDepartureDate())
						&& way.getLocationFrom().equals(trip.getLocationFrom())
						&& way.getLocationTo().equals(trip.getLocationTo()))
				.collect(Collectors.toList());
		System.out.println("Final ways are "+ways);
		return ways;
	}*/
}
