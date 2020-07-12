package com.ruslanproject.howtoget.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ruslanproject.howtoget.services.MyUserDetailService;

/**
 * Spring Security configuration
 * 
 * @author Ruslan Gainutdinov
 *
 */

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Autowired 
	private MyUserDetailService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	// Below is ready method to actual work
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/","/registration/**","/login","/contacts","/companies/**").permitAll()
		//.and()
		//.authorizeRequests(
		.antMatchers("/edit/**").hasAnyRole("ADMIN","PROVIDER")
		.antMatchers("/**").hasAnyRole("USER","ADMIN","PROVIDER")
		.and()
		.formLogin()
			.loginPage("/loginURL")
			.loginProcessingUrl("/authentication").defaultSuccessUrl("/")
			.permitAll()
		.and()
		.logout().logoutSuccessUrl("/");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
