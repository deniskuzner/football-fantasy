package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.MatchEvent;

public interface MatchEventService {

	void parseMatchEventsByMatchUrl(@NotNull String matchUrl);
	
	void parseMatchEventsByGameweekId(@NotNull @Min(1) Long gameweekId);

	MatchEvent save(MatchEvent matchEvent);

	MatchEvent findById(@NotNull @Min(1) Long id);
	
	List<MatchEvent> findByMatchId(@NotNull @Min(1) Long matchId);

	List<MatchEvent> findAll();

	void deleteById(@NotNull @Min(1) Long id);

}
