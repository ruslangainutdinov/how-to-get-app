package com.ruslanproject.howtoget.dao;

import java.util.Optional;

import com.ruslanproject.howtoget.entities.User;


public interface MyUserDetailsRepository {
	Optional<User> findUserByEmail(String email);
}
