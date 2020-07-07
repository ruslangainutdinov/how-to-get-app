package com.ruslanproject.howtoget.entities;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class for UserProfile
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Entity
@Table(name = "user_profiles")
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name = "userprofile_orders_bus",
				joinColumns = @JoinColumn(name="profile_id"),
				inverseJoinColumns = @JoinColumn(name="order_bus_id"))
	private List<OrderBus> ordersBus;
	

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name = "userprofile_orders_flight",
				joinColumns = @JoinColumn(name="profile_id"),
				inverseJoinColumns = @JoinColumn(name="order_flight_id"))
	private List<OrderFlight> ordersFlight;
	
	public List<Flight>  getFlightsOfOrder() {
		List<Flight> collect = ordersFlight.stream().map(b->b.getWay()).collect(Collectors.toList());
		return collect;
	}
	

	public List<Integer>  getBusesOfOrderIds() {
		List<Integer> collect = ordersBus.stream().map(b->b.getWay().getId()).collect(Collectors.toList());
		System.out.println("getBusesOfOrderIds()  "+collect);
		return collect;
	}
	
	public List<Integer>  getFlightsOfOrderIds() {
		List<Integer> collect = ordersFlight.stream().map(b->b.getWay().getId()).collect(Collectors.toList());
		System.out.println("getFlightfOrderIds()  "+collect);
		return collect;
	}
	
	
	public UserProfile() {
		
	}
	
	public UserProfile(User user) {
		this.user = user;
	}

	public void addBusOrder(OrderBus order) {
		if(order!=null) {
			ordersBus.add(order);
		}else
			throw new IllegalArgumentException("Order cannot be null");
	}
	
	public void addFlightOrder(OrderFlight order) {
		if(order!=null) {
			ordersFlight.add(order);
		}else
			throw new IllegalArgumentException("Order cannot be null");
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<OrderBus> getOrdersBus() {
		return ordersBus;
	}

	public void setOrdersBus(List<OrderBus> ordersBus) {
		this.ordersBus = ordersBus;
	}

	public List<OrderFlight> getOrdersFlight() {
		return ordersFlight;
	}

	public void setOrdersFlight(List<OrderFlight> ordersFlight) {
		this.ordersFlight = ordersFlight;
	}
}
