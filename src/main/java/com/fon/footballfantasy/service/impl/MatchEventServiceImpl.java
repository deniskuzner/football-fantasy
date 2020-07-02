package com.fon.footballfantasy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.parser.MatchPageHtmlParser;
import com.fon.footballfantasy.repository.GameweekRepository;
import com.fon.footballfantasy.repository.MatchEventRepository;
import com.fon.footballfantasy.service.MatchEventService;

@Service
@Transactional
@Validated
public class MatchEventServiceImpl implements MatchEventService {
	
	@Autowired
	MatchEventRepository matchEventRepository;
	
	@Autowired
	GameweekRepository gameweekRepository;
	
	@Autowired
	MatchPageHtmlParser matchPageHtmlParser;

	@Override
	public List<MatchEvent> parseMatchEventsByGameweekId(Long gameweekId) {
		List<MatchEvent> matchEvents = new ArrayList<>();
		Gameweek gameweek = gameweekRepository.findById(gameweekId).get();
		for (Match match : gameweek.getMatches()) {
			matchEvents.addAll(parseMatchEventsByMatchUrl(match.getUrl()));
		}
		return matchEvents;
	} 
	
	@Override
	public List<MatchEvent> parseMatchEventsByMatchUrl(String matchUrl) {
		List<MatchEvent> matchEvents = matchPageHtmlParser.parse(matchUrl);
		for (MatchEvent matchEvent : matchEvents) {
			save(matchEvent);
		}
		return matchEvents;
	}

	@Override
	public MatchEvent save(MatchEvent matchEvent) {
		return matchEventRepository.save(matchEvent);
	}

	@Override
	public List<MatchEvent> findByMatchId(Long matchId) {
		return matchEventRepository.findByMatchId(matchId);
	}

	@Override
	public void deleteById(Long id) {
		matchEventRepository.deleteById(id);
	}

}
