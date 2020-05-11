package com.ruslanproject.howtoget.services;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ruslanproject.howtoget.enities.MyUserDetails;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.repositories.UserRepository;

@Component
public class MyUserDetailService implements UserDetailsService{
	

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		User userFinal=user.orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
	
		return new MyUserDetails(userFinal);
	}
	
}
