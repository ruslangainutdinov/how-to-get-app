package com.ruslanproject.howtoget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableWebSecurity
public class HowToGetApplication {
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}
	
	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:static/messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HowToGetApplication.class, args);
	}
	
	
}

