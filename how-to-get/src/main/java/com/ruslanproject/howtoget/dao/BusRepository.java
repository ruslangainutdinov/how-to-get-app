package com.ruslanproject.howtoget.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.entities.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {
	
	List<Bus> findAllByCompanyProvider(String companyProvider);
	
	List<Bus> findAllByLocationFromAndLocationTo(String locationFrom,String locationTo);
	
	List<Bus> findAllByUfn(String ufn);
}
