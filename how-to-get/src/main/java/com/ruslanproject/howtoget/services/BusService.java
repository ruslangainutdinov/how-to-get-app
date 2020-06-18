package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.utils.WayToGetTransformer;

/**
 * Service class for BusService
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Service
public class BusService implements WayToGetService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
	
	@Autowired
	private BusRepository busRepository;
	
	//TODO @Autowired?
	private WayToGetTransformer<Bus> transformer = new WayToGetTransformer<>();
	
	@Transactional
	public List<Bus> findAllByTrip(Trip trip) {
		List<Bus> buses = busRepository.findAllByLocationFromAndLocationTo(trip.getLocationFrom(), trip.getLocationTo());
		
		buses= buses.stream().filter(b -> b.getDepartureDate().startsWith(trip.getDepartureDate())).collect(Collectors.toList());
		
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>Departuredate: "+trip.getDepartureDate());
		
		return buses;
	}
	
	
	@Transactional
	public void saveEntity(WayToGet way) {
		Bus bus = new Bus();
		
		transformer.transform(way, bus);
				
		busRepository.save(bus);
	}
}