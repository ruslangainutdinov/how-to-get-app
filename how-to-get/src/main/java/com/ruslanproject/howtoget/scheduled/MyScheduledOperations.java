package com.ruslanproject.howtoget.scheduled;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.services.CommercialAccountService;

@Component
public class MyScheduledOperations {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	BusRepository busRepository;	
	
	@Autowired
	CommercialAccountService commercialAccountService;

	
	@Scheduled(fixedDelay = 100000)
	@Transactional
	public void removeOldFlights() {

		List<Flight> flights = flightRepository.findAll();

		System.out.println("Flights" + flights);

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
				System.out.println("removeWay");
			}
			else {
				break OUTER;
			}
		}
	}
	
	@Scheduled(fixedDelay = 100000)
	@Transactional
	public void removeOldBuses() {

		List<Bus> buses = busRepository.findAll();

		System.out.println("Buses" + buses);

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
				System.out.println("Bus remove Way "+buses.get(i).getId() );
			}
			else {
				break OUTER;
			}
		}
	}
}