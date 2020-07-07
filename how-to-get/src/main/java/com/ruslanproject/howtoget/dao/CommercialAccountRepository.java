package com.ruslanproject.howtoget.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.entities.CommercialAccount;
import com.ruslanproject.howtoget.entities.UserProfile;

public interface CommercialAccountRepository extends JpaRepository<CommercialAccount, Integer> {
	CommercialAccount findByUserProfile(UserProfile userProfile);
}
