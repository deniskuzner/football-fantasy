package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Club;

public interface ClubService {
	
	List<Club> saveSeasonClubs();
	
	Club save(Club club);
	
	Club getById(@NotNull @Min(1) Long id);
	
	List<Club> getAll();
	
	void deleteById(@NotNull @Min(1) Long id);

}
