package com.ruslanproject.howtoget.aspects;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruslanproject.howtoget.dao.UserProfileRepository;
import com.ruslanproject.howtoget.enities.UserProfile;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.services.CommercialAccountService;
import com.ruslanproject.howtoget.utils.MailSenderClass;

/*Class responds for notification of Users whose booking was cancelled.
 * It's only applied when Transport provider removes it by itself. 
 * It doesn't applied to situation when booking is expired.
 * */
@Aspect
@Component
public class MailAspect {

	private static final Logger logger = LoggerFactory.getLogger(MailAspect.class);
	
	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private CommercialAccountService commercialAccountService;

	@Autowired
	private MailSenderClass sender;
	
	//>>>>>>Text constants which maybe? can be extracted to external properties file<<<<<<
	private static final String MESSAGE_HEADER="Hello ";
	
	private static final String MESSAGE_BODY="\n\nUnfortunately we have to inform you that your reservation was cancelled on this route: \n\n";
	
	private static final String MESSAGE_SUBJECT="Booking cancellation";
	
	private List<UserProfile> userProfiles;
	
	/*Pre-processing of removeWay() operation */
	@Before(value = "removeWay()")
	public void before(JoinPoint jPoint) {
		WayToGet way = (WayToGet) jPoint.getArgs()[0];
		Boolean aspectFlag=(Boolean)jPoint.getArgs()[1];
		System.out.println("Boolean?"+(Boolean)jPoint.getArgs()[1]);
		if(commercialAccountService.checkWayForBus(way)&&aspectFlag) {
			userProfiles= userProfileRepository.findAll().stream().filter(b->b.getBusesOfOrderIds().contains(way.getId())).collect(Collectors.toList());			
		}
		else if(commercialAccountService.checkWayForFlight(way)&&aspectFlag) {
			userProfiles= userProfileRepository.findAll().stream().filter(b->b.getFlightsOfOrderIds().contains(way.getId())).collect(Collectors.toList());
		}
		logger.info("Way to be removed: "+ way);
		logger.info("Users to inform: "+userProfiles);
	}
	
	/*Notification all the users about booking cancellation*/
	@AfterReturning(value = "removeWay()", returning = "result")
	public void after(JoinPoint jPoint, Boolean result) throws Throwable {
		WayToGet way = (WayToGet) jPoint.getArgs()[0];
		Boolean aspectFlag=(Boolean)jPoint.getArgs()[1];
		if (result&&aspectFlag) {
			userProfiles.stream()
				.forEach(b->{
					sender.sendMessage(b.getUser().getEmail(),null,MESSAGE_SUBJECT, 0L, 
							MESSAGE_HEADER+b.getUser().getFirstName()+" "+b.getUser().getLastName() +MESSAGE_BODY+ way.getCompanyProvider()
							+ " From: "+way.getLocationFrom()+ " To: "+ way.getLocationTo() + " "+ way.getDepartureDate()
					
					, false);
					});
		}
		if(aspectFlag)
		userProfiles.clear();
	}
	//pointcut expression for CommecrcialAccountService.removeWay(..)
	@Pointcut("execution(* com.ruslanproject.howtoget.services.CommercialAccountService.removeWay(com.ruslanproject.howtoget.enities.WayToGet,java.lang.Boolean))")
	public void removeWay() {}
	
}