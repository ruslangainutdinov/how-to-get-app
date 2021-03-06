package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.entities.Flight;
import com.ruslanproject.howtoget.entities.Trip;
import com.ruslanproject.howtoget.entities.WayToGet;
import com.ruslanproject.howtoget.utils.WayToGetTransformer;

/**
 * Service class for FlightService
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Service
public class FlightService implements WayToGetService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);

	@Autowired
	private FlightRepository flightRepository;
	
	private WayToGetTransformer<Flight> transformer = new WayToGetTransformer<>();

	@Transactional
	public List<Flight> findAllByTrip(Trip trip) {
		List<Flight> flights = flightRepository.findAllByLocationFromAndLocationTo(trip.getLocationFrom(), trip.getLocationTo());
		
		flights= flights.stream().filter(f->f.getDepartureDate().startsWith(trip.getDepartureDate())).collect(Collectors.toList());
		
		return flights;
	}
	
	@Transactional
	public void saveEntity(@Valid WayToGet way) {
		Flight flight = new Flight();
		transformer.transform(way, flight);
		LOGGER.info("Transformed flight: "+flight);	
		flightRepository.save(flight);
	}

}
