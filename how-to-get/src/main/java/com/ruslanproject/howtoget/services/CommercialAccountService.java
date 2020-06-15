package com.ruslanproject.howtoget.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.controllers.RegistrationUserController;
import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.CommercialAccountRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.dao.OrderBusRepository;
import com.ruslanproject.howtoget.dao.OrderFlightRepository;
import com.ruslanproject.howtoget.dao.ProviderRepository;
import com.ruslanproject.howtoget.dao.UserProfileRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.CommercialAccount;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.UserProfile;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.repositories.UserRepository;
import com.ruslanproject.howtoget.utils.WayToGetTransformer;

@Service
public class CommercialAccountService {

	private static final Logger logger = LoggerFactory.getLogger(CommercialAccountService.class);
	
	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private UfnStorageService ufnStorageService;

	@Autowired
	private OrderBusRepository orderBusRepository;

	@Autowired
	private OrderFlightRepository orderFlightRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private CommercialAccountRepository commercialAccountRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private WayToGetTransformer transformer;
	
	@Autowired
	private BusService busService;
	
	@Autowired
	private FlightService flightService;
	
	
	public List<? extends WayToGet> getAllWays(String email) {
		List<String> types = getTypesOfProvider(email);
		if (types.contains(WayToGet.Type.BUS.toString())) {
			System.out.println("Success enter");
			return getBusesByProvider(email);
			}
		if (types.contains(WayToGet.Type.FLIGHT.toString())) {
			System.out.println("Success enter");
			return getFlightsByProvider(email);
		}
		return null;
	}

	@Transactional
	private List<Bus> getBusesByProvider(String email) {
		CommercialAccount commercialAccount = null;
		try {
			commercialAccount = getCommercialAccount(email);
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
		return busRepository.findAllByCompanyProvider(commercialAccount.getCompanyName());
	}
	
	@Transactional
	private List<Flight> getFlightsByProvider(String email) {
		CommercialAccount commercialAccount = null;
		try {
			commercialAccount = getCommercialAccount(email);
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
		return flightRepository.findAllByCompanyProvider(commercialAccount.getCompanyName());
	}
	
	private List<String> getTypesOfProvider(String email) {
		String[] transportTypes = new String[] {};
		try {
			CommercialAccount commercialAccount = getCommercialAccount(email);
			transportTypes = commercialAccount.getTransportTypes().split(",");
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
		return Arrays.asList(transportTypes);
	}

	public CommercialAccount getCommercialAccount(String email) throws AccountNotFoundException {

		Optional<User> userContainer = userRepository.findByEmail(email);
		Optional<CommercialAccount> findByUserProfileId = null;
		if (userContainer.isPresent()) {
			findByUserProfileId = providerRepository.findByUserProfileId(userContainer.get().getUserProfile().getId());
		} else {
			throw new UsernameNotFoundException("User not found");
		}
		CommercialAccount commercialAccount = null;
		if (findByUserProfileId.isPresent()) {
			commercialAccount = findByUserProfileId.get();
		} else {
			throw new AccountNotFoundException("No suitable commercial account");
		}

		return commercialAccount;

	}

	@Transactional
	public boolean removeWay(WayToGet way, Boolean flag) {
		boolean done= false;
		if (checkWayForBus(way)) {
			System.out.println("Bus remove logic");
			Bus bus = new Bus();
			transformer.transform(way, bus);
			proceedOrdersBusDeletion(bus);
			orderBusRepository.deleteAllByWay(bus);
			busRepository.deleteById(bus.getId());
			done=true;
		} else if (checkWayForFlight(way)) {
			
			System.out.println("Flight remove logic");
			Flight flight = new Flight();
			
			
			transformer.transform(way, flight);
			
			proceedOrdersFlightDeletion(flight);
			
			orderFlightRepository.deleteAllByWay(flight);
			
			flightRepository.deleteById(flight.getId());
			
			done=true;
		}
		return done;
	}

	@Transactional
	private void proceedOrdersFlightDeletion(Flight flight) {
		userProfileRepository.findAll()
				.forEach(b -> b.getOrdersFlight().removeIf(c -> c.getWay().getId().equals(flight.getId())));
	}

	@Transactional
	private void proceedOrdersBusDeletion(Bus bus) {
		userProfileRepository.findAll()
				.forEach(b -> b.getOrdersBus().removeIf(c -> c.getWay().getId().equals(bus.getId())));
	}

	public boolean checkWayForBus(WayToGet way) {
		return ufnStorageService.getAllBusUfns().contains(way.getUfn());
	}

	public boolean checkWayForFlight(WayToGet way) {
		return ufnStorageService.getAllFlightUfns().contains(way.getUfn());
	}

	public WayToGet generateRoute(String type, String email) {
		
		UserProfile userProfile = userRepository.findByEmail(email).get().getUserProfile();
		CommercialAccount commercialAccount = commercialAccountRepository.findByUserProfile(userProfile);
		
		WayToGet way = null;
		switch (type) {
		case "Bus":
			way = new Bus();
			break;
		case "Flight":
			way = new Flight();
			break;
		}
		way.setCompanyProvider(commercialAccount.getCompanyName());
		
		return way;
	}
	
	public void saveEntity(@Valid WayToGet way, String type) {
		//TODO finish this logic with transform object
		
		if(way.getId()==null) {
			switch(type) {
			case "Flight":
				flightService.saveEntity(way);
				return;
			case "Bus":
				busService.saveEntity(way);
				return;
			}
		}
		
		System.out.println("Id: "+way.getId());
		System.out.println("Type: "+type);
		if(checkWayForBus(way)) {
			System.out.println("Saving bus Service");
			busService.saveEntity(way);
		}else if(checkWayForFlight(way)) {
			System.out.println("Saving flight Service");
			flightService.saveEntity(way);
		}
		
	}
}
