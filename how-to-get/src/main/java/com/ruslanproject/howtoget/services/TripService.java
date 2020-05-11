package com.ruslanproject.howtoget.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.BusDao;
import com.ruslanproject.howtoget.dao.FlightDao;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;

@Service
public class TripService {
	@Autowired
	BusDao busDao;
	
	@Autowired
	FlightDao flightDao;
	
	public List<Flight> getFlights(Trip trip) {
		List<Flight>flights = (List<Flight>) flightDao.getWays(trip);
		return flights;
	}
	
	public List<Bus> getBuses(Trip trip) {
		List<Bus>buses = (List<Bus>) busDao.getWays(trip);
		return buses;
	}

	public List<? extends WayToGet> filter(List<? extends WayToGet> ways, Trip trip) {
		System.out.println(ways);
		ways = ways.stream()
				.filter(way -> way.getDepartureDate().substring(0, 10).equals(trip.getDepartureDate())
						&& way.getLocationFrom().equals(trip.getLocationFrom())
						&& way.getLocationTo().equals(trip.getLocationTo()))
				.collect(Collectors.toList());
		System.out.println("Final ways are "+ways);
		return ways;
	}

}
