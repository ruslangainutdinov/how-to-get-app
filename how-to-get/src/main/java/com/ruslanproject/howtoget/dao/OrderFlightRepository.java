package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.OrderFlight;
import com.ruslanproject.howtoget.enities.WayToGet;

public interface OrderFlightRepository extends JpaRepository<OrderFlight, Integer> {
	void deleteAllByWay(WayToGet id);
}
