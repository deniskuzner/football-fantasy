package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.team.Team;

public interface TeamService {
	
	Team save(@NotNull Team team);
	
	Team update(@NotNull Team team);
	
	List<Team> findAll();
	
	Team findById(@NotNull @Min(1) Long id);
	
	Team findByUserId(@NotNull @Min(1) Long userId);
	
	void deleteById(@NotNull @Min(1) Long id);
	
	void deleteByUserId(@NotNull @Min(1) Long userId);

}
