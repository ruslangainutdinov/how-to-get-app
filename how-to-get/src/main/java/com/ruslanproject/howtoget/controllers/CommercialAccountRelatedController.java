package com.ruslanproject.howtoget.controllers;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.enities.CommercialAccount;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.services.CommercialAccountService;

/**
 * Controller to manage bookings by user who has role ROLE_
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Controller
@RequestMapping(value = "/myCabinet")
public class CommercialAccountRelatedController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommercialAccountRelatedController.class);
	
	@Autowired
	private CommercialAccountService commercialAccountService;
	
	/*commercial account related stuff
	 * providing list of owning ways to manage/edit */
	@RequestMapping("/edit")
	public String edit(Model model, Authentication auth) {
		
		List<? extends WayToGet> ways = commercialAccountService.getAllWays(auth.getName());
		
		model.addAttribute("companyWays", ways);
		
		LOG.info("Managing ways: "+ways);
				
		return "myCompanyCabinet";
	}
	
	@RequestMapping("/edit/remove")
	public String remove(@ModelAttribute("temp") WayToGet way, Authentication auth) {
		
		commercialAccountService.removeWay(way,true);
		
		LOG.info("Remove way" + way);
						
		return "redirect:/myCabinet/edit";
	}
	
	@RequestMapping("/edit/add")
	public String addNewWayPreCreate(Model model,Authentication auth) throws AccountNotFoundException {
		
		
		CommercialAccount commercialAccount = commercialAccountService.getCommercialAccount(auth.getName());
		
		LOG.debug("commercialAccount.getTransportTypes()"+commercialAccount.getTransportTypes());
		
		model.addAttribute("type", commercialAccount.getTransportTypes());
					
		return "addNewTripPreProcess";
	}
	
	
	@RequestMapping("/edit/add/new")
	public String addNewWayAdd(@RequestParam("type") String type,Model model, Authentication auth) {
		
		WayToGet way=commercialAccountService.generateRoute(type,auth.getName());

		LOG.info("Type to add is "+type);
		
		model.addAttribute("type", type);
		model.addAttribute("way", way);
		
		return "formToAddNewTrip";
	}
	
	
	@RequestMapping("/edit/add/save")
	public ModelAndView addNewWayAdd(@Valid@ModelAttribute("way") WayToGet way,BindingResult bindingResult, 
											@RequestParam("type") String type, Authentication auth) {
		
		ModelAndView model = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			model.addObject("way", way);
			model.setViewName("formToAddNewTrip");
			model.addObject("type", type);
			LOG.debug(">>>>>>adding way error: "+bindingResult.toString());
			return model;
		}		
		
		LOG.info("Processed way is "+way);
		
		commercialAccountService.saveEntity(way,type);
		
		model.setViewName("redirect:/succesSavingWay");
		return model;
	}
	//TODO maybe add valid from previous method
	@RequestMapping("/edit/edit/save")
	public ModelAndView editNewWayAdd(@ModelAttribute("way") WayToGet way, Authentication auth) {
		ModelAndView model = new ModelAndView();
		
		LOG.info("Processed way is "+way);

		model.setViewName("formToAddNewTrip");
		return model;
	}
}
