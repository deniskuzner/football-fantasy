package com.fon.footballfantasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.exception.GameweekException;
import com.fon.footballfantasy.exception.GameweekException.GameweekExceptionCode;
import com.fon.footballfantasy.parser.FixturesPageHtmlParser;
import com.fon.footballfantasy.repository.GameweekRepository;
import com.fon.footballfantasy.service.GameweekService;
import com.fon.footballfantasy.service.MatchService;

@Service
@Transactional
@Validated
public class GameweekServiceImpl implements GameweekService {
	
	@Autowired
	GameweekRepository gameweekRepository;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	FixturesPageHtmlParser fixturesParser;

	@Override
	public List<Gameweek> parseSeasonGameweeks() {
		List<Gameweek> gameweeks = fixturesParser.parse();
		gameweeks.forEach(gw -> save(gw));
		return gameweeks;
	}

	@Override
	public Gameweek save(Gameweek gameweek) {
		// Provera da li vec postoji u bazi gameweek
		Gameweek g = gameweekRepository.findByOrderNumber(gameweek.getOrderNumber());
		if(g != null) {
			gameweek.setId(g.getId());
			gameweek.setCreatedOn(g.getCreatedOn());
		}
		gameweekRepository.save(gameweek);
		gameweek.getMatches().forEach(m -> matchService.save(m));
		return gameweek;
	}

	@Override
	public Gameweek findById(Long id) {
		return gameweekRepository.findById(id).get();
	}

	@Override
	public Gameweek findByOrderNumber(int orderNumber) {
		return gameweekRepository.findByOrderNumber(orderNumber);
	}

	@Override
	public List<Gameweek> findAll() {
		return (List<Gameweek>) gameweekRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		gameweekRepository.deleteById(id);
	}

	@Override
	public long count() {
		return gameweekRepository.count();
	}

	@Override
	public Gameweek findCurrentGameweek() {
		Long id = gameweekRepository.findCurrentGameweekId();
		if(id == null) {
			return null;
		}
		return gameweekRepository.findById(id).get();
	}

	@Override
	public int findCurrentGameweekOrderNumber() {
		Long id = gameweekRepository.findCurrentGameweekId();
		if(id == null) {
			return 1;
		}
		return gameweekRepository.findOrderNumberById(id);
	}

}
