package com.ruslanproject.howtoget.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.entities.CommercialAccount;
import com.ruslanproject.howtoget.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight,Integer>{
	
	List<Flight> findAllByCompanyProvider(CommercialAccount companyProvider);
	
	List<Flight> findAllByLocationFromAndLocationTo(String locationFrom,String locationTo);
}
