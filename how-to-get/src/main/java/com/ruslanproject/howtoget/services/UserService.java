package com.ruslanproject.howtoget.services;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.UserRepository;
import com.ruslanproject.howtoget.enities.UniqueId;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.UserProfile;
import com.ruslanproject.howtoget.utils.MailSenderClass;

/**
 * Service class for UserService
 * 
 * @author Ruslan Gainutdinov
 *
 */

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

		User user = userMap.get(uniqueId.getEmail());
		
		UserProfile profile =new UserProfile();
		profile.setUser(user);
		user.setUserProfile(profile);
		
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		user.setEncryptedPassword(encodedPassword);
		userRepository.save(user);
		logger.info(">>>>>>Succesfully saved user to DB: "+user);
		userMap.remove(uniqueId.getEmail());
		logger.debug(">>>>>>userMap must do not contain "+uniqueId.getEmail()+" : "+validationMap.containsKey(uniqueId.getEmail()));
		validationMap.remove(uniqueId.getEmail());
		logger.debug(">>>>>>validationMap must do not contain "+uniqueId.getEmail()+" : "+validationMap.containsKey(uniqueId.getEmail()));
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
		logger.info("Code was send to: "+uniqueId.getEmail());
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
