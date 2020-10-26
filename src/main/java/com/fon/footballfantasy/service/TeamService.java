package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.team.Team;

public interface TeamService {
	
	List<Team> calculateGameweekPoints(@NotNull @Min(1) Long gameweekId);
	
	Team calculateTeamGameweekPoints(@NotNull Team team, @NotNull @Min(1) Long gameweekId);
	
	Team save(@NotNull Team team);
	
	List<Team> findAll();
	
	Team findById(@NotNull @Min(1) Long id);
	
	Team findByUserId(@NotNull @Min(1) Long userId);
	
	void deleteById(@NotNull @Min(1) Long id);
	
	void deleteByUserId(@NotNull @Min(1) Long userId);

}
