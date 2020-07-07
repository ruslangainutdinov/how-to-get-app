package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.entities.OrderBus;
import com.ruslanproject.howtoget.entities.WayToGet;

public interface OrderBusRepository extends JpaRepository<OrderBus, Long> {
	void deleteAllByWay(WayToGet id);
}
