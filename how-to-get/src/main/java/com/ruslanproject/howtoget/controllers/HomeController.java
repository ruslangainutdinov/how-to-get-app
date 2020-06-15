package com.ruslanproject.howtoget.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

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

import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.CommercialAccount;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.OrderBus;
import com.ruslanproject.howtoget.enities.OrderFlight;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.services.CommercialAccountService;
import com.ruslanproject.howtoget.services.OrderService;
import com.ruslanproject.howtoget.services.TripService;
import com.ruslanproject.howtoget.services.UserService;
import com.ruslanproject.howtoget.services.WayToGetService;

@Controller
public class HomeController {

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

	@Autowired
	private CommercialAccountService commercialAccountService;
	
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

		System.out.println("Types are: " + trip.getTypes());

		if (bindingResult.hasErrors()) {
			model.setViewName("form");
			return model;
		}
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
			List<Bus> buses= (List<Bus>) busService.findAllByTrip(trip);
			//List<Bus> buses = tripService.getBuses(trip);
			model.addObject("buses", buses);
		}
		if (trip.getTypes().contains("FLIGHT")) {
			List<Flight> fligths = (List<Flight>) flightService.findAllByTrip(trip);
			model.addObject("flights", fligths);
			System.out.println("Flights are " + fligths);
		}
		model.setViewName("processForm");
		return model;
	}

	/*Process 1 interested mapping*/
	@RequestMapping("/process/book")
	public String postBooking(@ModelAttribute("buses") WayToGet way, Model model, Authentication auth) {
		
		System.out.println("Way"+way);			

		model.addAttribute("way", way);
		
		System.out.println("Authentication.getName()"+auth.getName());
		
		return "bookTheWay";
	}

	/*Process targeted booking as a final order, with redirecting as a final booking*/
	@RequestMapping("/process/booking")
	public String processBooking(@ModelAttribute("way") WayToGet way, 
			@RequestParam(name="numberOfTickets") int number, Model model, Authentication auth) {
		
		System.out.println("HERE "+way);
		
		if(way.getTicketsAvailable()<number) {
			model.addAttribute("way", way);
			return "bookTheWay";
		}
		
		orderService.processNewOrder(way,number,auth.getName());
	
		System.out.println("Way "+way);
		System.out.println("Number: "+number);

		User user =userService.findByEmail(auth.getName());
		
		System.out.println("Step1 "+user.getUserProfile());
		System.out.println("Step2 ");
		System.out.println(auth.getName());
				
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
		
		System.out.println("list orderBus"+ordersBus);
	
		model.addAttribute("ordersBus",ordersBus);
		model.addAttribute("ordersFlight",ordersFlight);

		return "myOrders";
	}
	
	/*user's cancellation process already booked of 'ways'*/
	@PostMapping("/myOrders/cancel")
	public String cancelMyOrder(@RequestParam(name="id") int id,
								@RequestParam(name="ufn") String ufn,
								@RequestParam(name="number") int number,
								@RequestParam(name="wayid") int wayid,
														Authentication auth, Model model) {
		
		User user =userService.findByEmail(auth.getName());
		System.out.println("numberOfTickets"+number);
		System.out.println("User's ordersBus before: "+user.getUserProfile().getOrdersBus());
		System.out.println("User's ordersFlight before: "+user.getUserProfile().getOrdersFlight());
		
		System.out.println("Ufn: "+ufn);
		orderService.removeWay(id,ufn,auth.getName(),number,wayid);
		
		System.out.println("Id: "+id);
		System.out.println("User's ordersBus after: "+user.getUserProfile().getOrdersBus());
		System.out.println("User's ordersFlight after: "+user.getUserProfile().getOrdersFlight());
				
		return "redirect:/myOrders";
	}
	
	/*commercial account related stuff
	 * providing list of owning ways to manage/edit */
	@RequestMapping("/myCabinet/edit")
	public String edit(Model model, Authentication auth) {
		
		List<? extends WayToGet> ways = commercialAccountService.getAllWays(auth.getName());
		
		model.addAttribute("companyWays", ways);
		
		System.out.println("Managing ways: "+ways);
				
		return "myCompanyCabinet";
	}
	
	@RequestMapping("/myCabinet/edit/remove")
	public String remove(@ModelAttribute("temp") WayToGet way, Authentication auth) {
		
		commercialAccountService.removeWay(way,true);
		
		System.out.println("Remove way" + way);
						
		return "redirect:/myCabinet/edit";
	}
	
	@RequestMapping("/myCabinet/edit/add")
	public String addNewWayPreCreate(Model model,Authentication auth) throws AccountNotFoundException {
		
		
		CommercialAccount commercialAccount = commercialAccountService.getCommercialAccount(auth.getName());
		System.out.println("commercialAccount.getTransportTypes()"+commercialAccount.getTransportTypes());
		
		model.addAttribute("type", commercialAccount.getTransportTypes());
					
		return "addNewTripPreProcess";
	}
	
	
	@RequestMapping("/myCabinet/edit/add/new")
	public String addNewWayAdd(@RequestParam("type") String type,Model model, Authentication auth) {
		
		WayToGet way=commercialAccountService.generateRoute(type,auth.getName());
		
		System.out.println("Type to add is "+type);
		model.addAttribute("type", type);
		model.addAttribute("way", way);
		
		return "formToAddNewTrip";
	}
	
	
	@RequestMapping("/myCabinet/edit/add/save")
	public ModelAndView addNewWayAdd(@Valid@ModelAttribute("way") WayToGet way,BindingResult bindingResult, 
											@RequestParam("type") String type, Authentication auth) {
		
		ModelAndView model = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			model.addObject("way", way);
			model.setViewName("formToAddNewTrip");
			model.addObject("type", type);
			System.out.println(bindingResult);
			return model;
		}		
		
		System.out.println("Processed way is "+way);
		
		commercialAccountService.saveEntity(way,type);
		
		model.setViewName("redirect:/succesSavingWay");
		return model;
	}
	
	@RequestMapping("/myCabinet/edit/edit/save")
	public ModelAndView editNewWayAdd(@ModelAttribute("way") WayToGet way, Authentication auth) {
		ModelAndView model = new ModelAndView();
		
		System.out.println("Processed way is "+way);

		model.setViewName("formToAddNewTrip");
		return model;
	}
	
	
	
	
	
}