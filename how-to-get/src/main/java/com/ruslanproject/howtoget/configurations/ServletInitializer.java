package com.ruslanproject.howtoget.configurations;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ruslanproject.howtoget.HowToGetApplication;

/**
 * 
 * @author Ruslan Gainutdinov
 *
 */

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HowToGetApplication.class);
	}

}
