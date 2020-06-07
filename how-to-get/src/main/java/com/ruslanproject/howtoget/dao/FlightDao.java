package com.ruslanproject.howtoget.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ruslanproject.howtoget.enities.Flight;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;

@Repository
public class FlightDao {
	 
	@Transactional
	public List<? extends WayToGet> getFligths(Trip trip){
		 
		return null;
	 }
	
	/*public List<? extends WayToGet> getWays(Trip trip){
		List<WayToGet> ways = new ArrayList<>();
		ways.add(new Flight("FNW115","S7",50.00,"2020-05-05 08:00","1:20","New York","Washington D.C."));
		ways.add(new Flight("FNW115","S7",50.00,"2020-05-05 12:00","1:20","New York","Washington D.C."));
		ways.add(new Flight("FNW115","S7",50.00,"2020-05-05 18:00","1:20","New York","Washington D.C."));
		ways.add(new Flight("FNN552","Lufthansa",40.00,"2020-05-05 10:00","0:40","New York","New Jersey"));
		ways.add(new Flight("FBN314","S7",70.00,"2020-05-05 11:00","1:00","Boston","New York"));
		ways.add(new Flight("FBN314","S7",70.00,"2020-05-05 19:00","1:00","Boston","New York"));

		ways = ways.stream().filter(f -> f.getLocationFrom().equals(trip.getLocationFrom())
				&& f.getLocationTo().equals(trip.getLocationTo())).collect(Collectors.toList());
		return ways;
	}*/
}
