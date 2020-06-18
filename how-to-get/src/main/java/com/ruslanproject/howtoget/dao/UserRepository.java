package com.ruslanproject.howtoget.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ruslanproject.howtoget.enities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
