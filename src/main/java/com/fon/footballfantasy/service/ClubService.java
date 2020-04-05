package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Club;

public interface ClubService {
	
	List<Club> parseSeasonClubs();
	
	Club save(Club club);
	
	Club getById(@NotNull @Min(1) Long id);
	
	Club getByUrl(@NotNull String url);
	
	List<Club> getAll();
	
	void deleteById(@NotNull @Min(1) Long id);

}
