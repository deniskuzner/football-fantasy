package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.league.League;

public interface LeagueService {

	League save(@NotNull League league);

	List<League> findAll();

	League findById(@NotNull @Min(1) Long id);

	List<League> findByTeamId(@NotNull @Min(1) Long teamId);

	void deleteById(@NotNull @Min(1) Long id);

}
