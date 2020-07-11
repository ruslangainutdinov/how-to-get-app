package com.ruslanproject.howtoget.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruslanproject.howtoget.entities.CommercialAccount;
import com.ruslanproject.howtoget.entities.WayToGet;
import com.ruslanproject.howtoget.services.CommercialAccountService;
import com.ruslanproject.howtoget.utils.ApplicationMappings;
import com.ruslanproject.howtoget.utils.ApplicationViews;

/**
 * Controller to manage bookings by user who has role ROLE_
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Controller
@RequestMapping(value = ApplicationMappings.BASE_PATH_COMMERCIAL_ACCOUNT_CONTROLLER_MAPPING)
public class CommercialAccountRelatedController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommercialAccountRelatedController.class);
	
	@Autowired
	private CommercialAccountService commercialAccountService;
	
	/*commercial account related stuff
	 * providing list of owning ways to manage/edit */
	@RequestMapping(ApplicationMappings.MY_CABINET_EDIT_MAPPING)
	public String edit(Model model, Authentication auth,@RequestParam("page") Optional<Integer> pageNumber, 
		      											@RequestParam("size") Optional<Integer> size) {
		int currentPage=pageNumber.orElse(1);
		int sizePage = size.orElse(20);
		
		Page<WayToGet> page = commercialAccountService.findPaginated(PageRequest.of(currentPage-1, sizePage), auth.getName());
		
		model.addAttribute("page", page);
		
		int totalPages=page.getTotalPages();
		
		LOG.debug("Page content={}", page.getContent());
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        LOG.debug("Total pages ={}",page.getTotalPages());	
		return ApplicationViews.COMPANY_CABINET_VIEW;
	}
	
	@RequestMapping(ApplicationMappings.REMOVE_WAY_MAPPING)
	public String remove(@ModelAttribute("temp") WayToGet way, Authentication auth) {
		
		commercialAccountService.removeWay(way,true);
		
		LOG.info("Remove way" + way);
						
		return "redirect:"+ApplicationMappings.BASE_PATH_COMMERCIAL_ACCOUNT_CONTROLLER_MAPPING+ApplicationMappings.MY_CABINET_EDIT_MAPPING;
	}
	
	@RequestMapping(ApplicationMappings.ADD_WAY_TYPE_FORM_PRE_PROCESS_MAPPING)
	public String addNewWayPreCreate(Model model,Authentication auth) throws AccountNotFoundException {
		
		
		CommercialAccount commercialAccount = commercialAccountService.getCommercialAccount(auth.getName());
		
		LOG.info("commercialAccount.getTransportTypes()"+Arrays.toString(commercialAccount.getFormattedTransportTypes()));
		
		model.addAttribute("types", commercialAccount.getFormattedTransportTypes());
					
		return ApplicationViews.NEW_TRIP_PREPROCESS_VIEW;
	}
	
	
	@RequestMapping(ApplicationMappings.ADD_NEW_WAY_FORM_MAPPING)
	public String addNewWayAdd(@RequestParam("type") String type,Model model, Authentication auth) {
		WayToGet way=commercialAccountService.generateRoute(type,auth.getName());

		LOG.info("Type to add is "+type);
		
		model.addAttribute("type", type);
		model.addAttribute("way", way);
		
		return ApplicationViews.FORM_TO_ADD_NEW_WAY_VIEW;
	}
	//TODO maybe add valid from previous method
	@RequestMapping(ApplicationMappings.SAVE_EXISTED_WAY_MAPPING)
	public ModelAndView editNewWayAdd(@ModelAttribute("way") WayToGet way, Authentication auth) {
			ModelAndView model = new ModelAndView();
			
			LOG.info("Processed way is "+way);

			model.setViewName(ApplicationViews.FORM_TO_ADD_NEW_WAY_VIEW);
			return model;
	}
	
	
	@RequestMapping(ApplicationMappings.SAVE_NEW_WAY_MAPPING)
	public ModelAndView addNewWayAdd(@Valid@ModelAttribute("way") WayToGet way,BindingResult bindingResult, 
											@RequestParam("type") String type, Authentication auth) {
		ModelAndView model = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			model.addObject("way", way);
			model.setViewName(ApplicationViews.FORM_TO_ADD_NEW_WAY_VIEW);
			model.addObject("type", type);
			LOG.debug(">>>>>>adding way error: "+bindingResult.toString());
			return model;
		}		
		LOG.info("Processed way is "+way);
		
		commercialAccountService.saveEntity(way,type);
		
		model.setViewName("redirect:"+ApplicationMappings.BASE_PATH_COMMERCIAL_ACCOUNT_CONTROLLER_MAPPING+ApplicationMappings.MY_CABINET_EDIT_MAPPING);
		
		return model;
	}
}
