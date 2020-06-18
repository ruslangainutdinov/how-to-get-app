package com.ruslanproject.howtoget.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.dao.OrderBusRepository;
import com.ruslanproject.howtoget.dao.UserRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.OrderBus;
import com.ruslanproject.howtoget.enities.OrderFlight;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.utils.WayToGetTransformer;

/**
 * Service class for OrderService
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private BusRepository busRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderBusRepository orderBusRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommercialAccountService commercialAccountService;
	
	@Autowired
	private WayToGetTransformer transformer;
	
	public List<String> getAllBusUfns() {
		List<String> busesUfnList =busRepository.findAll().stream().map(b->b.getUfn()).distinct().collect(Collectors.toList());
		return busesUfnList;
	}
	
	public List<String> getAllFlightUfns() {
		List<String> flightUfnList =flightRepository.findAll().stream().map(b->b.getUfn()).distinct().collect(Collectors.toList());
		return flightUfnList;
	}
	
	public void processNewOrder(WayToGet way, int number, String name) {
		
		if(commercialAccountService.checkWayForFlight(way)) {
			//logic for fligths
			LOGGER.info("Flight logic");
			processNewOrderFligth(way,number,name);
		}
		else if (commercialAccountService.checkWayForBus(way)) {
			//logic for buses
			LOGGER.info("Bus logic");
			processNewOrderBus(way,number,name);
			
		} 
	}

	@Transactional
	private void processNewOrderBus(WayToGet way, int number, String name) {
		LOGGER.info("way.getId()"+way.getId());
		Optional<Bus> bus = busRepository.findById(way.getId());
		Optional<User> user = userRepository.findByEmail(name);
	
		Bus tempBus = bus.get();
		tempBus.setTicketsAvailable(tempBus.getTicketsAvailable()-number);
		busRepository.save(tempBus);
		
			if(bus.isPresent()&&user.isPresent()) {
				OrderBus order = new OrderBus(number, bus.get());
				User userToSave = user.get();
				userToSave.getUserProfile().addBusOrder(order);
				userRepository.save(userToSave);
			}
		
	}

	@Transactional
	private void processNewOrderFligth(WayToGet way, int number, String name) {
		
		LOGGER.info("way.getId()"+way.getId());
		
		Optional<Flight> flight = flightRepository.findById(way.getId());
		Optional<User> user = userRepository.findByEmail(name);
		
		Flight tempFlight = flight.get();
		tempFlight.setTicketsAvailable(tempFlight.getTicketsAvailable()-number);
		flightRepository.save(tempFlight);
				
		if(flight.isPresent()&&user.isPresent()) {
			OrderFlight order = new OrderFlight(number, flight.get());
			User userToSave = user.get();
			userToSave.getUserProfile().addFlightOrder(order);
			userRepository.save(userToSave);
		}
	}

	public List<OrderBus> getAllOrdersBusByUser(String name) {
		Optional<User> user =userRepository.findByEmail(name);
		if (user.isPresent())
			return user.get().getUserProfile().getOrdersBus();
		return new ArrayList<>();
	}
	
	public List<OrderFlight> getAllOrdersFlightByUser(String name) {
		Optional<User> user =userRepository.findByEmail(name);
		if (user.isPresent())
			return user.get().getUserProfile().getOrdersFlight();
		return new ArrayList<>();
	}
	
	public void order() {
	}

	public void deleteOrderBusById(int id) {
		orderBusRepository.deleteById((long) id);
	}
	public void saveUser(User userToSave) {
		userRepository.save(userToSave);

	}

	@Transactional
	public void removeWay(int id, String ufn, String email, int numberOfTickets, int wayid) {
		
		
		User user =userService.findByEmail(email);
		
		boolean bus = getAllBusUfns().contains(ufn);
		
		
		
		LOGGER.info("Ufns: "+getAllBusUfns());
		LOGGER.info("Ufns flight: "+getAllFlightUfns());
		LOGGER.info("Bus status: "+bus);
		
		if(getAllFlightUfns().contains(ufn)){
			LOGGER.info("Flight logic");
			
			flightUpdateNumberOfTickets(wayid, numberOfTickets);
			
			user.getUserProfile().getOrdersFlight().removeIf(i->i.getId()==(id));
		}
		else if (getAllBusUfns().contains(ufn)){
			LOGGER.info("Bus logic");
			
			busUpdateNumberOfTickets(wayid,numberOfTickets);
			
			user.getUserProfile().getOrdersBus().removeIf(i->i.getId()==(id));
		}
		
	}
	
	@Transactional
	private void flightUpdateNumberOfTickets(int id, int numberOfTickets) {
		Optional<Flight> optFlight = flightRepository.findById(id);
		Flight flight = optFlight.get();
		flight.setTicketsAvailable(flight.getTicketsAvailable()+numberOfTickets);
		flightRepository.save(flight);
	}

	@Transactional
	private void busUpdateNumberOfTickets(int id, int numberOfTickets) {
		Optional<Bus> optBus = busRepository.findById(id);
		LOGGER.info("busRepository.findById(id);"+id);
		Bus bus = optBus.get();
		bus.setTicketsAvailable(bus.getTicketsAvailable()+numberOfTickets);
		busRepository.save(bus);
	}
	
	
}