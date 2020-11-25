package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.league.TeamLeagueMembership;

public interface TeamLeagueMembershipService {
	
	TeamLeagueMembership save(@NotNull TeamLeagueMembership tlm);

	TeamLeagueMembership findById(@NotNull @Min(1) Long id);

	List<TeamLeagueMembership> findByTeamId(@NotNull @Min(1) Long teamId);
	
	List<TeamLeagueMembership> findByLeagueId(@NotNull @Min(1) Long leagueId);

	void deleteById(@NotNull @Min(1) Long id);

	void deleteByTeamAndLeague(@NotNull @Min(1) Long teamId, @NotNull @Min(1) Long leagueId);

}
