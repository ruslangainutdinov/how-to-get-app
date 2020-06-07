package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.OrderBus;
import com.ruslanproject.howtoget.enities.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{
	void deleteAllByOrdersBus(OrderBus orderBus);

}
