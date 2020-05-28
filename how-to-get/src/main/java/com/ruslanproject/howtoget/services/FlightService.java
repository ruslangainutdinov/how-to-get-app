package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.utils.WayToGetTransformer;

@Service
public class FlightService implements WayToGetService{
	
	@Autowired
	private FlightRepository flightRepository;
	
	private WayToGetTransformer<Flight> transformer = new WayToGetTransformer<>();
	
	@Transactional
	public List<Flight> findAll() {
		List<Flight> flights =flightRepository.findAll();
		return flights;
	}
	
	@Transactional
	public List<Flight> findAllByTrip(Trip trip) {
		List<Flight> flights = flightRepository.findAll();
		flights= flights.stream().filter(f -> f.getLocationFrom().equals(trip.getLocationFrom())
				&& f.getLocationTo().equals(trip.getLocationTo()) && (f.getTicketsAvailable()>0)).collect(Collectors.toList());
		
		return flights;
	}
	
	@Transactional
	public void saveEntity(@Valid WayToGet way) {
		Flight flight = new Flight();
		transformer.transform(way, flight);
		System.out.println("Transformed flight "+flight);	
		flightRepository.save(flight);
	}

}
