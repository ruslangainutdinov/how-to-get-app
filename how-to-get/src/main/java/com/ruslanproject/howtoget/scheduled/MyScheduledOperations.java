package com.ruslanproject.howtoget.scheduled;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.services.CommercialAccountService;

/**
 * Class is responsible for cleaning up expired ways-to-get(bookings)
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Component
public class MyScheduledOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduledOperations.class);
	
	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private BusRepository busRepository;	
	
	@Autowired
	private CommercialAccountService commercialAccountService;

	//TODO try to extract it to external properties file
	//every 4 minutes
	@Scheduled(fixedDelay = 240000)
	@Transactional
	public void removeOldFlights() {

		//TODO extract only today's flights!!!
		List<Flight> flights = flightRepository.findAll();

		LOGGER.debug("Today's flights" + flights);

		Collections.sort(flights, new Comparator<Object>() {

			@Override
			public int compare(Object arg0, Object arg1) {
				return ((Flight) arg1).getDepartureDate().compareTo(((Flight) arg0).getDepartureDate());
			}
		});

		
		OUTER:for (int i = flights.size() - 1; i >= 0; i--) {
			LocalDateTime departureDate = LocalDateTime.parse(flights.get(i).getDepartureDate());
			if(departureDate.isBefore(LocalDateTime.now())) {
				commercialAccountService.removeWay(flights.get(i),false);
				LOGGER.info("Flight was removed: "+flights.get(i).getId());
			}
			else {
				break OUTER;
			}
		}
	}
	
	//every 4 minutes
	@Scheduled(fixedDelay = 240000)
	@Transactional
	public void removeOldBuses() {
		//TODO extract only today's buses!!!

		List<Bus> buses = busRepository.findAll();

		LOGGER.debug("Today's buses: " + buses);

		Collections.sort(buses, new Comparator<Object>() {

			@Override
			public int compare(Object arg0, Object arg1) {
				return ((Bus) arg1).getDepartureDate().compareTo(((Bus) arg0).getDepartureDate());
			}
		});

		
		OUTER:for (int i = buses.size() - 1; i >= 0; i--) {
			LocalDateTime departureDate = LocalDateTime.parse(buses.get(i).getDepartureDate());
			if(departureDate.isBefore(LocalDateTime.now())) {
				commercialAccountService.removeWay(buses.get(i),false);
				LOGGER.info("Bus was removed: "+buses.get(i).getId());
			}
			else {
				break OUTER;
			}
		}
	}
}