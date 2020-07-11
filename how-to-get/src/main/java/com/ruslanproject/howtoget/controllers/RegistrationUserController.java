package com.ruslanproject.howtoget.controllers;

import java.util.Random;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.entities.UniqueId;
import com.ruslanproject.howtoget.entities.User;
import com.ruslanproject.howtoget.services.UserService;
import com.ruslanproject.howtoget.utils.ApplicationMappings;
import com.ruslanproject.howtoget.utils.ApplicationViews;


/**
 * Controller is responsible for registration procedure
 * 
 * @author Ruslan Gainutdinov
 *
 */


@Controller
@RequestMapping(ApplicationMappings.BASE_PATH_REGISTRATION_CONTROLLER_MAPPING)
public class RegistrationUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationUserController.class);
	
	private Random random = new Random();

	@Autowired
	private UserService userService;

	@GetMapping(ApplicationMappings.NEW_USER_MAPPING)
	public String getRegistrationForm(Model model) {

		User user = new User();

		model.addAttribute("user", user);
		
		return ApplicationViews.FORM_USER_REGISTRATION_VIEW;
	}
	


	@RequestMapping(ApplicationMappings.PROCESS_NEW_USER_MAPPING)
	public ModelAndView processRegistrationForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@ModelAttribute("uniqueId") UniqueId uniqueId) {
		ModelAndView model = new ModelAndView();		
		
		/*Check if user with provided email has already existed*/
		if(userService.isUserExists(user)){
			model.addObject("invalidCode", "User with this email already exists");
			model.setViewName(ApplicationViews.FORM_USER_REGISTRATION_VIEW);
			return model;
		}
	
		if(bindingResult.hasErrors()) {
			LOGGER.info(">>>>>>Binding result of registration: "+bindingResult);
			model.setViewName(ApplicationViews.FORM_USER_REGISTRATION_VIEW);
			return model;
		}
		String hexString;
		if (user.getEmail() != null) {
			if (userService.getValidationMap().containsKey(user.getEmail())) {
				hexString = userService.getValidationMap().get(user.getEmail()).getUniqueId();
				
			} else {
				long confirmationCode = Math.abs(random.nextLong());
				hexString = Long.toHexString(confirmationCode);
				UniqueId uniqueIdNew = new UniqueId(hexString, user.getEmail());
				userService.sendConfirmation(uniqueIdNew,user);
				userService.setUniqueId(uniqueIdNew);
			}
		}
		userService.setUserToMap(user.getEmail(), user);

		UniqueId modelUniqueId = new UniqueId();

		modelUniqueId.setEmail(user.getEmail());
		
		if(uniqueId!=null) {
			System.out.println("Unique id's email: "+uniqueId.getEmail());
			modelUniqueId.setEmail(user.getEmail());
		}
		
		
		// here send email with this actual code
		
		model.addObject("modelUniqueId", modelUniqueId);
		model.addObject("invalidCode", "");
		model.setViewName(ApplicationViews.CONFIRMATION_CODE_PAGE_VIEW);
		LOGGER.info(">>>>>>User's email: " + user.getEmail());
		return model;
	}

	@PostMapping(ApplicationMappings.CONFIRM_NEW_USER_MAPPING)
	public ModelAndView confirmNewUser(@Valid @ModelAttribute UniqueId uniqueId, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		
		
		System.out.println("Second: "+uniqueId.getEmail());
		if (userService.validateUser(uniqueId)) {
			model.setViewName("redirect:"+ApplicationMappings.BASE_PATH_REGISTRATION_CONTROLLER_MAPPING+ApplicationMappings.SUCCESS_REGISTRATION_MAPPING);			
			LOGGER.debug(">>>>>>Succes registration: "+userService.getValidationMap());
			userService.processUserToDB(uniqueId);
			return model;
		} else {
			model.addObject("invalidCode", "Please enter valid code.");
			UniqueId id = new UniqueId();
			id.setEmail(uniqueId.getEmail());
			model.addObject("modelUniqueId", id);
			model.setViewName(ApplicationViews.CONFIRMATION_CODE_PAGE_VIEW);
			return model;

		}
	}
}
