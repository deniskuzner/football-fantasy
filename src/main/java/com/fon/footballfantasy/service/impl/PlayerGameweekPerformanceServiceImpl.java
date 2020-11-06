package com.fon.footballfantasy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.calculator.MatchPerformanceCalculator;
import com.fon.footballfantasy.calculator.PlayerPriceCalculator;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.repository.PlayerGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.PlayerRepository;
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
	PlayerRepository playerRepository;
	
	@Autowired
	MatchPerformanceCalculator playerPerformanceCalculator;
	
	@Autowired
	PlayerPriceCalculator priceCalculator;
	
	@Override
	public List<PlayerGameweekPerformance> calculateByDate(MatchSearchRequest searchRequest) {
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		List<Match> matches = matchService.searchMatches(searchRequest);
		for (Match match : matches) {
			if(match.getEvents().size() == 0)
				continue;
			if(matchService.updateSent(match.getId()) == 1) {
				List<PlayerGameweekPerformance> matchPerformances = playerPerformanceCalculator.getMatchPerformances(match);
				performances.addAll(saveAll(matchPerformances));
			}
		}
		return performances;
	}

	@Override
	public List<PlayerGameweekPerformance> calculateByGameweek(int gameweekOrderNumber) {
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		Gameweek gameweek = gameweekService.findByOrderNumber(gameweekOrderNumber);
		List<Match> matches = matchService.findByGameweekId(gameweek.getId());
		for (Match match : matches) {
			if(match.getEvents().size() == 0)
				continue;
			if(matchService.updateSent(match.getId()) == 1) {
				List<PlayerGameweekPerformance> matchPerformances = playerPerformanceCalculator.getMatchPerformances(match);
				performances.addAll(saveAll(matchPerformances));
			}
		}
		return performances;
	}

	@Override
	public List<PlayerGameweekPerformance> saveAll(List<PlayerGameweekPerformance> playerGameweekPerformances) {
		for (PlayerGameweekPerformance pgp : playerGameweekPerformances) {
			Player player = pgp.getPlayer();
			
			// update player total points and price
			int totalPoints = player.getTotalPoints() + pgp.getPoints();
			double price = priceCalculator.getUpdatedPrice(pgp);
			playerRepository.updateTotalPointsAndPrice(player.getId(), totalPoints, price);
			
			performanceRepository.save(pgp);
		}
		return playerGameweekPerformances;
	}

	@Override
	public List<PlayerGameweekPerformance> findByPlayerId(Long playerId) {
		return performanceRepository.findByPlayerId(playerId);
	}

}
