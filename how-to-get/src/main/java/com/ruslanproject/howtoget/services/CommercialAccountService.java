package com.ruslanproject.howtoget.services;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.ProviderRepository;
import com.ruslanproject.howtoget.enities.CommercialAccount;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.repositories.UserRepository;

@Service
public class CommercialAccountService {
	

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private UserRepository userRepository;

	// TODO finish implementation of this class
	//public List<? extends WayToGet> 
	
	
	
	public String[] getTypesOfProvider(String email) {
		String[] transportTypes = new String[] {};
		try {
			CommercialAccount commercialAccount = getCommercialAccount(email);
			transportTypes =commercialAccount.getTransportTypes().split(",");
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transportTypes;
	}
	
	
	public CommercialAccount getCommercialAccount(String email) throws AccountNotFoundException {
	
		Optional<User> userContainer = userRepository.findByEmail(email);
		Optional<CommercialAccount> findByUserProfileId =null;
		if(userContainer.isPresent()) {
			findByUserProfileId = providerRepository.findByUserProfileId(userContainer.get().getUserProfile().getId());
		}else {
			throw new UsernameNotFoundException("User not found");
		}
		CommercialAccount commercialAccount=null;
		if(findByUserProfileId.isPresent()) {
			commercialAccount = findByUserProfileId.get();
		}
		else {
			throw new AccountNotFoundException("No suitable commercial account");
		}
			
		return commercialAccount;
	}
}
