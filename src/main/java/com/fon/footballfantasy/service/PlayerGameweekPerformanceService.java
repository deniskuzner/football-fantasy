package com.fon.footballfantasy.service;

import java.util.List;

import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.service.dto.MatchSearchRequest;

public interface PlayerGameweekPerformanceService {
	
	List<PlayerGameweekPerformance> calculateByDate(MatchSearchRequest searchRequest);
	
	List<PlayerGameweekPerformance> calculateByGameweek(Long gameweekId);

}
