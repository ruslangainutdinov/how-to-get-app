package com.ruslanproject.howtoget.enities;

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

@Entity
@Table(name="orders_flight")
public class OrderFlight {
	
	/*When order is created the number of tickets must be at least 1*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="tickets_number")
	private int numberOfTickets=1;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
						CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
	@JoinColumn(name = "flight")
	private Flight way;
	
	public OrderFlight() {
		
	}
	
	public OrderFlight(int numberOfTickets, Flight way) {
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

	public Flight getWay() {
		return way;
	}

	public void setWay(Flight way) {
		this.way = way;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", numberOfTickets=" + numberOfTickets + ", way=" + way + "]";
	}
	
}
