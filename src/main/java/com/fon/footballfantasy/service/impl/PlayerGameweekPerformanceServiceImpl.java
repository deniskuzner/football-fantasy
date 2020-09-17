package com.fon.footballfantasy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.calculator.MatchPerformanceCalculator;
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
	
	@Autowired
	MatchPerformanceCalculator playerPerformanceCalculator;

	@Override
	public List<PlayerGameweekPerformance> calculateByDate(MatchSearchRequest searchRequest) {
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		List<Match> matches = matchService.searchMatches(searchRequest);
		for (Match match : matches) {
			if(matchService.updateSent(match.getId()) == 1) {
				List<PlayerGameweekPerformance> matchPerformances = playerPerformanceCalculator.getMatchPerformances(match);
				performances.addAll(saveAll(matchPerformances));
			}
		}
		return performances;
	}

	@Override
	public List<PlayerGameweekPerformance> calculateByGameweek(Long gameweekId) {
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		List<Match> matches = matchService.findByGameweekId(gameweekId);
		for (Match match : matches) {
			if(matchService.updateSent(match.getId()) == 1) {
				List<PlayerGameweekPerformance> matchPerformances = playerPerformanceCalculator.getMatchPerformances(match);
				performances.addAll(saveAll(matchPerformances));
			}
		}
		return performances;
	}

	@Override
	public List<PlayerGameweekPerformance> saveAll(List<PlayerGameweekPerformance> playerGameweekPerformances) {
		performanceRepository.saveAll(playerGameweekPerformances);
		return playerGameweekPerformances;
	}

}
