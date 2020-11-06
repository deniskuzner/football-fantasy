package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Gameweek;

public interface GameweekService {

	List<Gameweek> parseSeasonGameweeks();

	Gameweek save(Gameweek gameweek);

	Gameweek findById(@NotNull @Min(1) Long id);
	
	Gameweek findByOrderNumber(@NotNull @Min(1) int orderNumber);

	List<Gameweek> findAll();

	void deleteById(@NotNull @Min(1) Long id);

	long count();
	
	Gameweek findCurrentGameweek();
	
	int findCurrentGameweekOrderNumber();

}
