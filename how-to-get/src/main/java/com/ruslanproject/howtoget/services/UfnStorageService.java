package com.ruslanproject.howtoget.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.entities.Bus;

/**
 * Service class for UfnStorageService
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Service
public class UfnStorageService {

	private static final Logger logger = LoggerFactory.getLogger(UfnStorageService.class);
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private BusRepository busRepository;
		
	public boolean getAllBusUfns(String ufn) {
		
	//	List<String> busUfns = busRepository.findAll().stream().map(w->w.getUfn()).distinct().collect(Collectors.toList());
		List<Bus> busUfns = busRepository.findAllByUfn(ufn);
		
		return !busUfns.isEmpty();
	}
	
	public boolean getAllFlightUfns(String ufn) {
		
		List<String> flightUfns = flightRepository.findAll().stream().map(w->w.getUfn()).distinct().collect(Collectors.toList());
		
		
		return flightUfns.contains(ufn);
	}
	
}
