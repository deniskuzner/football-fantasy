package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.setup.GameweekSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GameweekRepositoryTest extends BaseRepositoryTest {

	@Autowired
	GameweekRepository gameweekRepository;
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	GameweekSetup gameweekSetup;
	
	@Test
	void testCrud() {

		log.info("Getting gameweek setup");
		Gameweek gameweek = gameweekSetup.getSetup();
		
		log.info("Getting gameweek with matches");
		gameweek = gameweekRepository.findById(gameweek.getId()).get();
		assertNotNull(gameweek);
		assertEquals(1, gameweek.getOrderNumber());
		assertEquals(2, gameweek.getMatches().size());
		
		log.info("Delete gameweek with matches");
		gameweekRepository.deleteById(gameweek.getId());
		assertEquals(Optional.empty(), gameweekRepository.findById(gameweek.getId()));
		assertEquals(0, ((List<Match>) matchRepository.findAll()).size());
		
	}
	
	@Test
	void testFindCurrentGameweekId() {
		Gameweek gameweek = gameweekSetup.getSetup();
		Long gameweekId = gameweekRepository.findCurrentGameweekId();
		assertEquals(gameweek.getId(), gameweekId);
	}
	
}
