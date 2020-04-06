package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.Gameweek;

public interface GameweekRepository extends CrudRepository<Gameweek, Long> {
	
	Gameweek findByOrderNumber(int orderNumber);

}
