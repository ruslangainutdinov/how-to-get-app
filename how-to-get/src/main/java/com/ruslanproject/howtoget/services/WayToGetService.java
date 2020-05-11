package com.ruslanproject.howtoget.services;

import java.util.List;

import com.ruslanproject.howtoget.enities.WayToGet;

public interface WayToGetService {
	List<? extends WayToGet> findAll();
}
