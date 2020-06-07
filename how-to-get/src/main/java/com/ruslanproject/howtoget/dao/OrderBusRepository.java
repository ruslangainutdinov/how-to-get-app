package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.OrderBus;
import com.ruslanproject.howtoget.enities.WayToGet;

public interface OrderBusRepository extends JpaRepository<OrderBus, Long> {
	void deleteAllByWay(WayToGet id);
}
