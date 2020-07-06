package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.entities.OrderFlight;
import com.ruslanproject.howtoget.entities.WayToGet;

public interface OrderFlightRepository extends JpaRepository<OrderFlight, Integer> {
	void deleteAllByWay(WayToGet id);
}
