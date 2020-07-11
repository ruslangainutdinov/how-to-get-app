package com.ruslanproject.howtoget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ruslanproject.howtoget.utils.ApplicationMappings;
import com.ruslanproject.howtoget.utils.ApplicationViews;

@SpringBootApplication
@EnableScheduling
public class HowToGetApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(HowToGetApplication.class, args);
	}

}


