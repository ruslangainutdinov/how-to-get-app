package com.ruslanproject.howtoget.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class for OrderBus
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Entity
@Table(name="orders_bus")
public class OrderBus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	/*When order is created the number of tickets must be at least 1*/
	@Column(name="tickets_number")
	private int numberOfTickets=1;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
						CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
	@JoinColumn(name = "bus")
	private Bus way;
	
	public OrderBus() {		
	
	}
	
	
	public OrderBus(int numberOfTickets, Bus way) {
		this.numberOfTickets = numberOfTickets;
		this.way = way;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bus getWay() {
		return way;
	}

	public void setWay(Bus bus) {
		this.way = bus;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", numberOfTickets=" + numberOfTickets + ", way=" + way + "]";
	}
	
}
