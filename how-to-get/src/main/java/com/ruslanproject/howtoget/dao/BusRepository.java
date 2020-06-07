package com.ruslanproject.howtoget.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {
	
	List<Bus> findAllByCompanyProvider(String companyProvider);
	
	List<Bus> findAllByLocationFromAndLocationTo(String locationFrom,String locationTo);
}
