package com.ruslanproject.howtoget.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruslanproject.howtoget.enities.CommercialAccount;

public interface ProviderRepository extends JpaRepository<CommercialAccount, Integer> {
	Optional<CommercialAccount> findByUserProfileId(int id);
}
