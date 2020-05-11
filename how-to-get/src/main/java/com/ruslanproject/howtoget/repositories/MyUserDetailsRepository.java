package com.ruslanproject.howtoget.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruslanproject.howtoget.enities.User;


public interface MyUserDetailsRepository {
	Optional<User> findUserByEmail(String email);
}
