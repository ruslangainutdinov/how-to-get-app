package com.ruslanproject.howtoget.services;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.enities.UniqueId;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.UserProfile;
import com.ruslanproject.howtoget.repositories.UserRepository;
import com.ruslanproject.howtoget.utils.MailSenderClass;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailSenderClass sender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String MESSAGE_SUBJECT="Registration confirmation";
	
	private HashMap<String,UniqueId> validationMap = new HashMap<>();

	private HashMap<String,User> userMap = new HashMap<>();
	
	public boolean isUserExists(User user) {
		boolean userStatus=false;
		Optional<User> userFromDb =userRepository.findByEmail(user.getEmail());
		if(userFromDb.isPresent()) {
			userStatus=true;
		}
		return userStatus;
	}
	
	
	public void setUserToMap(String email, User user) {
		userMap.put(email, user);
	}
	
	public void processUserToDB(UniqueId uniqueId) {
		System.out.println("Step 1");
		User user = userMap.get(uniqueId.getEmail());
		System.out.println("Step 2"+userMap);
		UserProfile profile =new UserProfile();
		profile.setUser(user);
		user.setUserProfile(profile);
		
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		user.setEncryptedPassword(encodedPassword);
		System.out.println("encodedPassword"+encodedPassword);
		userRepository.save(user);
		System.out.println("Step 3");
		userMap.remove(uniqueId.getEmail());
		System.out.println("Step 4");
		validationMap.remove(uniqueId.getEmail());
	}
	
	public void setUniqueId(UniqueId uniqueId) {
			validationMap.put(uniqueId.getEmail(), uniqueId);
	}
	
	public UniqueId getUniqueId(@Valid User user) {
		return validationMap.get(user.getEmail());
	}

	public void setValidationMap(HashMap<String, UniqueId> validationMap) {
		this.validationMap = validationMap;
	}

	public HashMap<String, UniqueId> getValidationMap() {
		return validationMap;
	}

	public boolean validateUser(@Valid UniqueId uniqueId) {
		boolean result = false;
		if(getValidationMap().containsKey(uniqueId.getEmail())) {
			result=getValidationMap().get(uniqueId.getEmail()).equals(uniqueId);
		}
		return result;
	}

	public void sendConfirmation(UniqueId uniqueId, @Valid User user) {
		System.out.println("Value to be send: "+uniqueId.getUniqueId());
		sender.sendMessage(uniqueId.getEmail(), user.getFirstName()+" "+user.getLastName()
		,MESSAGE_SUBJECT, uniqueId.getUniqueId(), null, true);

	}
	
	public User findByEmail(String email) {
		Optional<User> user=userRepository.findByEmail(email);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
}
