package com.fon.footballfantasy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.repository.PlayerGameweekPerformanceRepository;
import com.fon.footballfantasy.service.GameweekService;
import com.fon.footballfantasy.service.MatchService;
import com.fon.footballfantasy.service.PlayerGameweekPerformanceService;
import com.fon.footballfantasy.service.dto.MatchSearchRequest;

@Service
@Transactional
@Validated
public class PlayerGameweekPerformanceServiceImpl implements PlayerGameweekPerformanceService {
	
	@Autowired
	PlayerGameweekPerformanceRepository performanceRepository;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	GameweekService gameweekService;

	@Override
	public List<PlayerGameweekPerformance> calculateByDate(MatchSearchRequest searchRequest) {
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		List<Match> matches = matchService.searchMatches(searchRequest);
		for (Match match : matches) {
			//List<PlayerGameweekPerformance> matchPerformances = calculator.get(match);
			//save(matchPerformances);
			//performances.addAll(matchPerformances);
		}
		return null;
	}

	@Override
	public List<PlayerGameweekPerformance> calculateByGameweek(Long gameweekId) {
		// TODO Auto-generated method stub
		return null;
	}

}
