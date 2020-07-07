package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.entities.OrderBus;
import com.ruslanproject.howtoget.entities.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{
	void deleteAllByOrdersBus(OrderBus orderBus);

}
