package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.service.dto.TeamGameweekStats;

public interface TeamGameweekPerformanceService {
	
	List<Team> calculateGameweekPoints(@NotNull @Min(1) Long gameweekId);
	
	TeamGameweekStats getGameweekStats(@NotNull @Min(1) Long teamId, @NotNull @Min(1) Long gameweekId);
	
}
