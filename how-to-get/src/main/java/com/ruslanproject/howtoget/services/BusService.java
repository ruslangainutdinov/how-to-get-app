package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.utils.WayToGetTransformer;

@Service
public class BusService implements WayToGetService{
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private BusRepository busRepository;
	
	private WayToGetTransformer<Bus> transformer = new WayToGetTransformer<>();
	
	@Transactional
	public List<Bus> findAllByTrip(Trip trip) {
		List<Bus> buses = busRepository.findAllByLocationFromAndLocationTo(trip.getLocationFrom(), trip.getLocationTo());
		
		buses= buses.stream().filter(b -> b.getDepartureDate().startsWith(trip.getDepartureDate())).collect(Collectors.toList());
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>Departuredate: "+trip.getDepartureDate());
		return buses;
	}
	
	
	
	@Transactional
	public void saveEntity(WayToGet way) {
		Bus bus = new Bus();
		transformer.transform(way, bus);
		System.out.println("Transformed bus "+bus);	
		busRepository.save(bus);
	}
}