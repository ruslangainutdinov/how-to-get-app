package com.ruslanproject.howtoget.enities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ruslanproject.howtoget.dao.UserRepository;

/**
 * Entity class for MyUserDetails.
 * Used as SpringSecurity Entity in the first place
 * 
 * @author Ruslan Gainutdinov
 *
 */

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = -5032784401290896927L;

	// private MyUserRepository userDetailsRepository = new MyUserRepository();

	private User user;	

	@Autowired
	UserRepository userRepository;
	
	public MyUserDetails() {
		
	}
	
	
	public MyUserDetails(User user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roles = user.getRoles();
		String[] rolesArray = roles.split(",");
		System.out.println("rolesArray:"+Arrays.toString(rolesArray));
		List<SimpleGrantedAuthority> authorities =Arrays.stream(rolesArray).map(b->new SimpleGrantedAuthority(b)).collect(Collectors.toList());
		
		return authorities;
	}

	@Override
	public String getPassword() {
		System.out.println(">>>>>>>>>>>>>>>>Username is " + user.getEmail());
		// userDetailsRepository.getUser(username);
		return user.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
