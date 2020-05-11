package com.ruslanproject.howtoget.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.dao.BusRepository;
import com.ruslanproject.howtoget.dao.FlightRepository;
import com.ruslanproject.howtoget.dao.OrderBusRepository;
import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.OrderBus;
import com.ruslanproject.howtoget.enities.OrderFlight;
import com.ruslanproject.howtoget.enities.User;
import com.ruslanproject.howtoget.enities.WayToGet;
import com.ruslanproject.howtoget.repositories.UserRepository;

@Service
public class OrderService {

	@Autowired
	BusRepository busRepository;
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderBusRepository orderBusRepository;
	
	@Autowired
	UserService userService;
	
	
	
	public List<String> getAllBusUfns() {
		List<String> busesUfnList =busRepository.findAll().stream().map(b->b.getUfn()).distinct().collect(Collectors.toList());
		return busesUfnList;
	}
	
	public List<String> getAllFlightUfns() {
		List<String> flightUfnList =flightRepository.findAll().stream().map(b->b.getUfn()).distinct().collect(Collectors.toList());
		return flightUfnList;
	}
	
	public void processNewOrder(WayToGet way, int number, String name) {
		
		if(way.getUfn().startsWith("F")) {
			//logic for fligths
			System.out.println("Flight logic");
			processNewOrderFligth(way,number,name);
		}
		else if (way.getUfn().startsWith("N")) {
			//logic for buses
			System.out.println("Bus logic");
			processNewOrderBus(way,number,name);
			
		} 
	}

	private void processNewOrderBus(WayToGet way, int number, String name) {
		System.out.println("way.getId()"+way.getId());
		Optional<Bus> bus = busRepository.findById(way.getId());
		Optional<User> user = userRepository.findByEmail(name);
			if(bus.isPresent()&&user.isPresent()) {
				OrderBus order = new OrderBus(number, bus.get());
				User userToSave = user.get();
				userToSave.getUserProfile().addBusOrder(order);
				userRepository.save(userToSave);
			}
		
	}

	private void processNewOrderFligth(WayToGet way, int number, String name) {
		System.out.println("way.getId()"+way.getId());
		Optional<Flight> flight = flightRepository.findById(way.getId());
		Optional<User> user = userRepository.findByEmail(name);
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
	public void removeWay(int id, String ufn, String email) {
		User user =userService.findByEmail(email);
		boolean bus = getAllBusUfns().contains(ufn);
		System.out.println("Ufns: "+getAllBusUfns());
		System.out.println("Ufns flight: "+getAllFlightUfns());
		System.out.println("Bus status: "+bus);
		if(getAllFlightUfns().contains(ufn)){
			//logic for fligths
			System.out.println("Flight logic");
			user.getUserProfile().getOrdersFlight().removeIf(i->i.getId()==(id));
		}
		else if (getAllBusUfns().contains(ufn)){
			System.out.println("Bus logic");
			user.getUserProfile().getOrdersBus().removeIf(i->i.getId()==(id));
		}
		
	}
}