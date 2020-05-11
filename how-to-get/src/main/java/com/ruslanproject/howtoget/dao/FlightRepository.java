package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.Flight;

public interface FlightRepository extends JpaRepository<Flight,Integer>{
	
}
