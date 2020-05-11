package com.ruslanproject.howtoget.controllers;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.enities.UniqueId;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.services.UserService;
import com.ruslanproject.howtoget.utils.MailSenderClass;

@Controller
@RequestMapping("/registration")
public class RegistrationUserController {

	private Random random = new Random();

	@Autowired
	private UserService userService;

	@GetMapping("/newUser")
	public String getRegistrationForm(Model model) {

		User user = new User();

		model.addAttribute("user", user);
		return "userRegistrationForm";
	}

	@RequestMapping("/process")
	public ModelAndView processRegistrationForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@ModelAttribute("uniqueId") UniqueId uniqueId) {
		ModelAndView model = new ModelAndView();		
		
		/*Check if user with provided email has already existed*/
		if(userService.isUserExists(user)){
			model.addObject("invalidCode", "User with this email already exists");
			model.setViewName("userRegistrationForm");
			return model;
		}
	
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			model.setViewName("userRegistrationForm");
			return model;
		}
		long confirmationCode;
		if (user.getEmail() != null) {
			if (userService.getValidationMap().containsKey(user.getEmail())) {
				confirmationCode = userService.getValidationMap().get(user.getEmail()).getUniqueId();
			} else {
				confirmationCode = Math.abs(random.nextLong());
				System.out.println("Code: " + confirmationCode);
				UniqueId uniqueIdNew = new UniqueId(confirmationCode, user.getEmail());
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
		model.setViewName("confirmationCodePage");
		System.out.println("User email first: " + user.getEmail());
		return model;
	}

	@PostMapping("/confirm")
	public ModelAndView confirmNewUser(@Valid @ModelAttribute UniqueId uniqueId, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		
		
		System.out.println("Second: "+uniqueId.getEmail());
		if (userService.validateUser(uniqueId)) {
			model.setViewName("successRegistration");			
			System.out.println("Succes registration: "+userService.getValidationMap());
			userService.processUserToDB(uniqueId);
			return model;
		} else {
			// userService.getValidationMap().get(uniqueId.getEmail()).incrementCount();
			model.addObject("invalidCode", "Please enter valid code.");
			UniqueId id = new UniqueId();
			id.setEmail(uniqueId.getEmail());
			// user.setEmail(uniqueId.getEmail());
			model.addObject("modelUniqueId", id);
			model.setViewName("confirmationCodePage");
			return model;

		}
	}
}
