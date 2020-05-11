package com.ruslanproject.howtoget.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.OrderBus;
import com.ruslanproject.howtoget.enities.OrderFlight;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.services.FlightService;
import com.ruslanproject.howtoget.services.OrderService;
import com.ruslanproject.howtoget.services.TripService;
import com.ruslanproject.howtoget.services.UserService;

@Controller
public class HomeController {

	@Autowired
	TripService tripService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FlightService flightService;
	
	@Autowired
	OrderService orderService;

	@RequestMapping("/contacts")
	public String getContacts() {

		return "info";
	}

	@RequestMapping("/login")
	public String getLoginForm() {
		return "loginForm";
	}

	@RequestMapping("/")
	public String getHome(Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("authenticationName", authentication.getName());
		}
		return "index";
	}

	@RequestMapping("/tripForm")
	public String getForm(Model model, Authentication authentication) {

		model.addAttribute("authenticationName", authentication.getName());

		model.addAttribute("tripObject", new Trip());
		List<String> cities = new ArrayList();
		cities.add("New York");
		cities.add("Washington D.C.");
		cities.add("Boston");

		model.addAttribute("cities", cities);
		// model.addAttribute("ways", new String[] {"BUS","FLIGHT"});
		return "form";
	}

	@PostMapping("/process")
	public ModelAndView postForm(@Valid @ModelAttribute("tripObject") Trip trip, BindingResult bindingResult,Authentication authentication
			) {
		ModelAndView model = new ModelAndView();
		model.addObject("authenticationName", authentication.getName());

		List<String> cities = new ArrayList();
		cities.add("New York");
		cities.add("Washington D.C.");
		cities.add("Boston");

		System.out.println("Types are: " + trip.getTypes());

		System.out.println("Types are: " + trip.getArrivalDate());

		model.addObject("cities", cities);

		if (bindingResult.hasErrors()) {
			model.setViewName("form");
			return model;
		}

		// model.addAttribute("tripObject", new Trip("New York","London","21.08.2020"));
		System.out.println(trip);
		trip.setTimestamp(LocalDateTime.now());
		// System.out.println(trip.getDepartureDate());
		/*
		 * trip.setDepartureDate(LocalDate.now().toString()); if
		 * (trip.getDepartureDate() != null) { LocalDate date =
		 * LocalDate.parse(trip.getDepartureDate());
		 * System.out.println(">>>>>>>>>>>>>>>date: " + date); }
		 */
		model.setViewName("form");
		if ((trip.getDepartureDate().isEmpty())
				|| (LocalDate.parse(trip.getDepartureDate()).isBefore(trip.getTimestamp().toLocalDate()))) {
			model.addObject("errorStatus", "Departure date must be after");
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
			List<Bus> buses= flightService.findBuses(trip);
			//List<Bus> buses = tripService.getBuses(trip);
			model.addObject("buses", buses);
		}
		if (trip.getTypes().contains("FLIGHT")) {
			List<Flight> fligths = flightService.findFlights(trip);
			model.addObject("flights", fligths);
			System.out.println("Flights are " + fligths);
		}
		model.setViewName("processForm");
		return model;
	}

	@RequestMapping("/process/book")
	public String postBooking(@ModelAttribute("buses") WayToGet way, Model model, Authentication auth) {
		
		
		System.out.println("Way"+way);			

		model.addAttribute("way", way);
		
		System.out.println("Authentication.getName()"+auth.getName());
		
		
		System.out.println("Way: " + way);
		return "bookTheWay";
	}

	@RequestMapping("/process/booking")
	public String processBooking(@ModelAttribute("way") WayToGet way, 
			@RequestParam(name="numberOfTickets") int number, Model model, Authentication auth) {
		System.out.println("HERE "+way);
		
		
		orderService.processNewOrder(way,number,auth.getName());
		
		
		System.out.println("HERE "+way);
		System.out.println("Number: "+number);

		User user =userService.findByEmail(auth.getName());
		System.out.println("Step1 "+user.getUserProfile());
		System.out.println("Step2 ");
		
		System.out.println(auth.getName());
		System.out.println("Fligths"+flightService.findFlights());
				
		return "succesBooking";
	}
	
	@RequestMapping("/user")
	public String getMyCabinet(@RequestParam(name="id") String email, Model model) {
		User user =userService.findByEmail(email);
		
		if(user!=null) {
			model.addAttribute("user", user);
		}else {
			model.addAttribute("invalidUser", "User not found");
		}
		return "myCabinet";
	}
	@RequestMapping("/myOrders")
	public String getMyOrders(Authentication auth, Model model) {
		
		List<OrderBus> ordersBus= orderService.getAllOrdersBusByUser(auth.getName());
		List<OrderFlight> ordersFlight= orderService.getAllOrdersFlightByUser(auth.getName());
		
		System.out.println("list orderBus "+ordersBus);
	
		model.addAttribute("ordersBus",ordersBus);
		model.addAttribute("ordersFlight",ordersFlight);
		
		return "myOrders";
	}
	
	@PostMapping("/myOrders/cancel")
	public String cancelMyOrder(@RequestParam(name="id") int id,@RequestParam(name="ufn") String ufn,
														Authentication auth, Model model) {
		
		User user =userService.findByEmail(auth.getName());

		System.out.println("User's ordersBus before: "+user.getUserProfile().getOrdersBus());
		System.out.println("User's ordersFlight before: "+user.getUserProfile().getOrdersFlight());
		
		
		System.out.println("Ufn: "+ufn);
		orderService.removeWay(id,ufn,auth.getName());
		
		
		
		
		//orderService.deleteOrderBusById(id);
		
		System.out.println("Id: "+id);
		System.out.println("User's ordersBus after: "+user.getUserProfile().getOrdersBus());
		System.out.println("User's ordersFlight after: "+user.getUserProfile().getOrdersFlight());
		
		//orderService.saveUser(user);
		
		return "info";
	}
}