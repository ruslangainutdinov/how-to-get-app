package com.ruslanproject.howtoget.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ruslanproject.howtoget.enities.Bus;
import com.ruslanproject.howtoget.enities.Trip;
import com.ruslanproject.howtoget.enities.WayToGet;

@Repository
public class BusDao {
	public List<? extends WayToGet> getWays(Trip trip) {
		List<WayToGet> ways = new ArrayList<>();
		/*ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 00:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 02:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 04:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 06:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 08:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 10:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 12:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 14:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 16:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 18:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 20:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NW11", "Flix Bus", 14.90, "2020-05-09 22:00", "3:45", "New York", "Washington D.C."));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 00:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 02:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 04:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 06:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 08:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 10:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 12:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 14:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 16:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 18:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 20:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("NN14", "Hitchike.com", 22.15, "2020-05-09 22:00", "1:50", "New York", "New Jersey"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 06:00", "5:45", "Boston", "New York"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 08:00", "5:45", "Boston", "New York"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 10:00", "5:45", "Boston", "New York"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 12:00", "5:45", "Boston", "New York"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 14:00", "5:45", "Boston", "New York"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 16:00", "5:45", "Boston", "New York"));
		ways.add(new Bus("BW31", "Flix Bus", 30.00, "2020-05-09 18:00", "5:45", "Boston", "New York"));*/
		ways = ways.stream().filter(f -> f.getLocationFrom().equals(trip.getLocationFrom())
				&& f.getLocationTo().equals(trip.getLocationTo())).collect(Collectors.toList());
		return ways;
	}
}
