package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.Trip;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private BusRepository busRepository;
	
	public List<Flight> findFlights() {
		List<Flight> flights =flightRepository.findAll();
		return flights;
	}
	
	public List<Bus> findBuses(Trip trip) {
		List<Bus> buses = busRepository.findAll();
		buses= buses.stream().filter(f -> f.getLocationFrom().equals(trip.getLocationFrom())
				&& f.getLocationTo().equals(trip.getLocationTo())).collect(Collectors.toList());
		
		return buses;
	}
	
	public List<Flight> findFlights(Trip trip) {
		List<Flight> flights = flightRepository.findAll();
		flights= flights.stream().filter(f -> f.getLocationFrom().equals(trip.getLocationFrom())
				&& f.getLocationTo().equals(trip.getLocationTo())).collect(Collectors.toList());
		
		return flights;
	}

}
