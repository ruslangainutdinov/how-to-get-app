package com.ruslanproject.howtoget.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ruslanproject.howtoget.entities.CommercialAccount;
import com.ruslanproject.howtoget.entities.OrderBus;
import com.ruslanproject.howtoget.entities.OrderFlight;
import com.ruslanproject.howtoget.entities.User;
import com.ruslanproject.howtoget.services.CommercialAccountService;
import com.ruslanproject.howtoget.services.OrderService;
import com.ruslanproject.howtoget.services.UserService;
import com.ruslanproject.howtoget.services.WayToGetService;
import com.ruslanproject.howtoget.utils.ApplicationMappings;
import com.ruslanproject.howtoget.utils.ApplicationViews;

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


	/*user cabinet mapping*/
	@GetMapping(ApplicationMappings.MY_CABINET_MAPPING)
	public String getMyCabinet(@RequestParam(name="id") String email, Model model) {
		User user =userService.findByEmail(email);
		
		if(user!=null) {
			model.addAttribute("user", user);
		}else {
			model.addAttribute("invalidUser", "User not found");
		}
		return ApplicationViews.MY_CABINET_VIEW;
	}
	
	
	/*user's personal orders*/
	@RequestMapping(ApplicationMappings.MY_ORDERS_MAPPING)
	public String getMyOrders(Authentication auth, Model model) {
		
		List<OrderBus> ordersBus= orderService.getAllOrdersBusByUser(auth.getName());
		List<OrderFlight> ordersFlight= orderService.getAllOrdersFlightByUser(auth.getName());
		
		LOGGER.info(">>>>>>User's list orderBus: "+ordersBus);
		LOGGER.info(">>>>>>User's list ordersFlight: "+ordersFlight);
		
		model.addAttribute("ordersBus",ordersBus);
		model.addAttribute("ordersFlight",ordersFlight);

		return ApplicationViews.MY_ORDERS_VIEW;
	}
	
	/*user's cancellation process already booked of 'ways'*/
	@PostMapping(ApplicationMappings.MY_ORDERS_CANCELLATION_MAPPING)
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
				
		return "redirect:"+ApplicationMappings.MY_ORDERS_MAPPING;
	}
	
	@GetMapping(ApplicationMappings.ALL_COMPANIES_MAPPING)
	public String getTransportCompanies(Model model) {
		List<CommercialAccount> commercialAccounts = commercialAccountService.getCommercialAccounts();
		
		model.addAttribute("commercialAccounts",commercialAccounts);
		
		return ApplicationViews.ALL_COMPANIES_VIEW;
	}
	
	@GetMapping(ApplicationMappings.SINGLE_COMPANY_MAPPING)
	public String getSpecificCompany(@RequestParam("id")Integer id, Model model) {
		CommercialAccount commercialAccountById = commercialAccountService.getCommercialAccountById(id);

		model.addAttribute("commercialAccountById", commercialAccountById);
		
		return ApplicationViews.SINGLE_COMPANY_VIEW;
	}
	

}