package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Club;

public interface ClubService {
	
	List<Club> parseSeasonClubs();
	
	Club save(Club club);
	
	Club findById(@NotNull @Min(1) Long id);
	
	// mozda nije potrebno
	Club findByUrl(@NotNull String url);
	
	List<Club> findAll();
	
	void deleteById(@NotNull @Min(1) Long id);

}
