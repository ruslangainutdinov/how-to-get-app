package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.OrderBus;

public interface OrderBusRepository extends JpaRepository<OrderBus, Long> {

}
