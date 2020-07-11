/**
 * 
 */
package com.ruslanproject.howtoget.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ruslanproject.howtoget.utils.ApplicationMappings;
import com.ruslanproject.howtoget.utils.ApplicationViews;

/**
 * @author Ruslan
 *
 */

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(ApplicationMappings.CONTACTS_MAPPING).setViewName(ApplicationViews.INFO_VIEW);
		registry.addViewController(ApplicationMappings.SUCCESS_BOOKING_MAPPING).setViewName(ApplicationViews.SUCCESS_BOOKING_VIEW);
		registry.addViewController(ApplicationMappings.SUCCESS_SAVING_WAY_MAPPING).setViewName(ApplicationViews.SUCCESS_SAVING_WAY_VIEW);
		registry.addViewController(ApplicationMappings.LOGIN_URL_MAPPING).setViewName(ApplicationViews.FORM_LOGIN_VIEW);
		registry.addViewController(ApplicationMappings.HOME_PAGE_MAPPING).setViewName(ApplicationViews.HOME_VIEW);
		registry.addViewController(ApplicationMappings.BASE_PATH_REGISTRATION_CONTROLLER_MAPPING+ApplicationMappings.SUCCESS_REGISTRATION_MAPPING)
				.setViewName(ApplicationViews.SUCCESS_REGISTRATION_VIEW);
	}
	
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
	

}
