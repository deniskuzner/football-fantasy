package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.event.MatchEvent;

public interface MatchEventService {

	List<MatchEvent> parseMatchEventsByGameweekId(@NotNull @Min(1) Long gameweekId);
	
	List<MatchEvent> parseMatchEventsByMatchUrl(@NotNull String matchUrl);	

	MatchEvent save(MatchEvent matchEvent);

	List<MatchEvent> findByMatchId(@NotNull @Min(1) Long matchId);
	
	void deleteById(@NotNull @Min(1) Long id);

	//Future<List<MatchEventMessage>> sendMatchEvents(SendMatchEventsRequest request);
	
}
