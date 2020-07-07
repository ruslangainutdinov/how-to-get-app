package com.ruslanproject.howtoget.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ruslanproject.howtoget.dao.UserRepository;
import com.ruslanproject.howtoget.entities.MyUserDetails;
import com.ruslanproject.howtoget.entities.User;

/**
 * Service class for MyUserDetailService
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Service
public class MyUserDetailService implements UserDetailsService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		User userFinal=user.orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
	
		return new MyUserDetails(userFinal);
	}
	
}
