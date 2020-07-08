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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.entities.Bus;
import com.ruslanproject.howtoget.entities.Flight;
import com.ruslanproject.howtoget.entities.OrderBus;
import com.ruslanproject.howtoget.entities.OrderFlight;
import com.ruslanproject.howtoget.entities.Trip;
import com.ruslanproject.howtoget.entities.User;
import com.ruslanproject.howtoget.entities.WayToGet;
import com.ruslanproject.howtoget.services.OrderService;
import com.ruslanproject.howtoget.services.TripService;
import com.ruslanproject.howtoget.services.UserService;
import com.ruslanproject.howtoget.services.WayToGetService;

/**
 * Main Home Controller
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("busService")
	private WayToGetService busService;
	
	@Autowired
	@Qualifier("flightService")
	private WayToGetService flightService;
	
	@Autowired
	private OrderService orderService;

	
	
	/*Basic info application*/
	@RequestMapping("/contacts")
	public String getContacts() {
		return "info";
	}	
	
	/*Booking related mapping*/
	@RequestMapping("/successBooking")
	public String getSuccesBooking() {
		return "succesBooking";
	}
	
	/*Booking editing mapping*/
	@RequestMapping("/succesSavingWay")
	public String getSuccesSavingWay() {
		return "succesSavingWay";
	}

	/*login stuff may be related to home*/
	@RequestMapping("/loginURL")
	public String getLoginForm() {
		return "loginForm";
	}

	/*home page*/
	@RequestMapping("/")
	public String getHome(Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("authenticationName", authentication.getName());
		}
		return "index";
	}

	/*Start searching for concrete way-to-get*/
	@RequestMapping("/tripForm")
	public String getForm(Model model, Authentication authentication) {

		model.addAttribute("authenticationName", authentication.getName());

		model.addAttribute("tripObject", new Trip());
		List<String> locations=tripService.getAllLocations();

		model.addAttribute("locations", locations);
		return "form";
	}

	/*Respond with list of possible target ways-to-get*/
	@PostMapping("/process")
	public ModelAndView postForm(@Valid @ModelAttribute("tripObject") Trip trip, BindingResult bindingResult,Authentication authentication
			) {
		ModelAndView model = new ModelAndView();
		
		model.addObject("authenticationName", authentication.getName());

		List<String> locations=tripService.getAllLocations();

		model.addObject("locations", locations);

		LOGGER.info(">>>>>>Types are: " + trip.getTypes());

		if (bindingResult.hasErrors()) {
			model.setViewName("form");
			return model;
		}
		LOGGER.info(">>>>>Trip to process: "+trip.toString());
		trip.setTimestamp(LocalDateTime.now());
		// LOGGER.info(trip.getDepartureDate());
		/*
		 * trip.setDepartureDate(LocalDate.now().toString()); if
		 * (trip.getDepartureDate() != null) { LocalDate date =
		 * LocalDate.parse(trip.getDepartureDate());
		 * LOGGER.info(">>>>>>>>>>>>>>>date: " + date); }
		 */
		model.setViewName("form");
		if ((trip.getDepartureDate().isEmpty())
				|| (LocalDate.parse(trip.getDepartureDate()).isBefore(trip.getTimestamp().toLocalDate()))) {
			model.addObject("errorStatus", "Departure date cannot be in past");
			return model;
		}
		/*if (trip.getLocationFrom().equals(trip.getLocationTo())) {
			model.addObject("errorStatus", "Departure location and arrival location cannot match");
			return model;
		}*/
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
		model.setViewName("processForm");
		return model;
	}

	/*Process 1 interested booking*/
	@RequestMapping("/process/book")
	public String postBooking(@ModelAttribute("buses") WayToGet way, Model model, Authentication auth) {
		
		LOGGER.info(">>>>>>Way to process: "+way);			

		model.addAttribute("way", way);
		
		LOGGER.debug(">>>>>>Authentication.getName() :"+auth.getName());
		
		return "bookTheWay";
	}

	/*Process targeted booking as a final order, with redirecting as a final booking*/
	@RequestMapping("/process/booking")
	public String processBooking(@ModelAttribute("way") WayToGet way, 
			@RequestParam(name="numberOfTickets") int numberOfTickets, Model model, Authentication auth) {
		
		LOGGER.info(">>>>>>Process way as a new ORDER: "+way);
		
		if(way.getTicketsAvailable()<numberOfTickets) {
			model.addAttribute("way", way);
			return "bookTheWay";
		}
		
		orderService.processNewOrder(way,numberOfTickets,auth.getName());
	
		LOGGER.info(">>>>>>Number of tickets: "+numberOfTickets);
				
		LOGGER.debug(">>>>>>Authentication.getName() :"+auth.getName());
				
		return "redirect:/successBooking";
	}
	
	/*user cabinet mapping*/
	@GetMapping("/user")
	public String getMyCabinet(@RequestParam(name="id") String email, Model model) {
		User user =userService.findByEmail(email);
		
		if(user!=null) {
			model.addAttribute("user", user);
		}else {
			model.addAttribute("invalidUser", "User not found");
		}
		return "myCabinet";
	}
	
	
	/*user's personal orders*/
	@RequestMapping("/myOrders")
	public String getMyOrders(Authentication auth, Model model) {
		
		List<OrderBus> ordersBus= orderService.getAllOrdersBusByUser(auth.getName());
		List<OrderFlight> ordersFlight= orderService.getAllOrdersFlightByUser(auth.getName());
		
		LOGGER.info(">>>>>>User's list orderBus: "+ordersBus);
		LOGGER.info(">>>>>>User's list ordersFlight: "+ordersFlight);
		
		model.addAttribute("ordersBus",ordersBus);
		model.addAttribute("ordersFlight",ordersFlight);

		return "myOrders";
	}
	
	/*user's cancellation process already booked of 'ways'*/
	@PostMapping("/myOrders/cancel")
	public String cancelMyOrder(@RequestParam(name="id") int id,
								@RequestParam(name="ufn") String ufn,
								@RequestParam(name="number") int numberOfTickets,
								@RequestParam(name="wayid") int wayid,
														Authentication auth, Model model) {
		
		User user =userService.findByEmail(auth.getName());
		LOGGER.info(">>>>>>User's ordersBus before: "+user.getUserProfile().getOrdersBus());
		LOGGER.info(">>>>>>User's ordersFlight before: "+user.getUserProfile().getOrdersFlight());
		
		LOGGER.info("Ufn: "+ufn);
		
		orderService.removeWay(id,ufn,auth.getName(),numberOfTickets,wayid);
		
		LOGGER.info(">>>>>>Id: "+id+", ufn: "+ufn+", numberOfTickets: "+numberOfTickets);
		LOGGER.info(">>>>>>User's ordersBus after: "+user.getUserProfile().getOrdersBus());
		LOGGER.info(">>>>>>User's ordersFlight after: "+user.getUserProfile().getOrdersFlight());
				
		return "redirect:/myOrders";
	}
	
	@GetMapping("/companies")
	public String getTransportCompanies(Model model) {
		//get all transport companies
		//add them to the model
		//return view template
		return null;
	}
	
	@GetMapping("/companies/company")
	public String getSpecificCompany(@RequestParam("id")Integer id, Model model) {
		//get specific commercial account according to its id
		//add found account to the model
		return null;
	}
	

}