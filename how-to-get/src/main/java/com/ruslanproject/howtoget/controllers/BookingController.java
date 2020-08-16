/**
 * 
 */
package com.ruslanproject.howtoget.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.entities.Bus;
import com.ruslanproject.howtoget.entities.Flight;
import com.ruslanproject.howtoget.entities.Trip;
import com.ruslanproject.howtoget.entities.WayToGet;
import com.ruslanproject.howtoget.services.OrderService;
import com.ruslanproject.howtoget.services.TripService;
import com.ruslanproject.howtoget.services.WayToGetService;
import com.ruslanproject.howtoget.utils.ApplicationMappings;
import com.ruslanproject.howtoget.utils.ApplicationViews;

/**
 * @author Ruslan
 *
 */
@Controller
public class BookingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	@Qualifier("busService")
	private WayToGetService busService;
	
	@Autowired
	@Qualifier("flightService")
	private WayToGetService flightService;
	
	/*Start searching for concrete way-to-get*/
	@RequestMapping(ApplicationMappings.SEARCH_TRIP_MAPPING)
	public String getForm(Model model, Authentication authentication) {

		model.addAttribute("authenticationName", authentication.getName());
		model.addAttribute("tripObject", new Trip());
		
		List<String> locations=tripService.getAllLocations();
		//TODO refactor method above
		model.addAttribute("locations", locations);
		
		return ApplicationViews.FORM_TRIP_VIEW;
	}
	
	/*Respond with list of possible target ways-to-get*/
	@PostMapping(ApplicationMappings.PROCESS_TRIP_MAPPING)
	public ModelAndView postForm(@Valid @ModelAttribute("tripObject") Trip trip, BindingResult bindingResult,Authentication authentication
			) {
		ModelAndView model = new ModelAndView();
		
		model.addObject("authenticationName", authentication.getName());

		List<String> locations=tripService.getAllLocations();
		model.addObject("locations", locations);

		LOGGER.info(">>>>>>Types are: " + trip.getTypes());

		if (bindingResult.hasErrors()) {
			model.setViewName(ApplicationViews.FORM_TRIP_VIEW);
			return model;
		}
		LOGGER.info(">>>>>Trip to process: "+trip.toString());
		trip.setTimestamp(LocalDateTime.now());

		model.setViewName(ApplicationViews.FORM_TRIP_VIEW);
		if ((trip.getDepartureDate().isEmpty())
				|| (LocalDate.parse(trip.getDepartureDate()).isBefore(trip.getTimestamp().toLocalDate()))) {
			model.addObject("errorStatus", "Departure date cannot be in past");
			return model;
		}

		if (trip.getTypes().isEmpty()) {
			model.addObject("errorStatus", "Please pick the way you want to go");
			return model;
		}

		if (trip.getTypes().contains("BUS")) {
			@SuppressWarnings("unchecked")
			List<Bus> buses= (List<Bus>) busService.findAllByTrip(trip);
			//List<Bus> buses = tripService.getBuses(trip);
			model.addObject("buses", buses);
			LOGGER.info(">>>>>>Buses are " + buses);
		}
		if (trip.getTypes().contains("FLIGHT")) {
			@SuppressWarnings("unchecked")
			List<Flight> fligths = (List<Flight>) flightService.findAllByTrip(trip);
			model.addObject("flights", fligths);
			LOGGER.info(">>>>>>Flights are " + fligths);
		}
		model.setViewName(ApplicationViews.TRIP_SEARCH_RESULTS_VIEW);
		return model;
	}
	
	/*Process 1 specific booking*/
	@RequestMapping(ApplicationMappings.PROCESS_BOOKING_MAPPING)
	public String postBooking(@ModelAttribute("buses") WayToGet way, Model model, Authentication auth) {
		LOGGER.info(">>>>>>Way to process: "+way);			

		model.addAttribute("way", way);
		
		LOGGER.debug(">>>>>>Authentication.getName() :"+auth.getName());
		
		return ApplicationViews.BOOK_WAY_VIEW;
	}
	
	/*Process targeted booking as a final order, with redirecting as a final booking*/
	@RequestMapping(ApplicationMappings.CONFIRM_BOOKING_MAPPING)
	public String processBooking(@ModelAttribute("way") WayToGet way, 
			@RequestParam(name="numberOfTickets") int numberOfTickets, Model model, Authentication auth) {
		
		LOGGER.info(">>>>>>Process way as a new ORDER: "+way);
		
		if(way.getTicketsAvailable()<numberOfTickets) {
			model.addAttribute("way", way);
			return ApplicationViews.BOOK_WAY_VIEW;
		}
		
		orderService.processNewOrder(way,numberOfTickets,auth.getName());
	
		LOGGER.info(">>>>>>Number of tickets: "+numberOfTickets);
				
		LOGGER.debug(">>>>>>Authentication.getName() :"+auth.getName());
				
		return "redirect:"+ApplicationMappings.SUCCESS_BOOKING_MAPPING;
	}
	
}
