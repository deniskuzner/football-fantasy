package com.fon.footballfantasy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.repository.BaseRepositoryTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GameweekServiceTest extends BaseRepositoryTest {
	
	@Autowired
	GameweekService gameweekService;
	
	@Autowired
	MatchService matchService;

	@Test
	void testParseSeasonGameweek() {
		
		log.info("Parse season gameweeks");
		
		gameweekService.parseSeasonGameweeks();
		assertEquals(30, gameweekService.findAll().size());
		assertEquals(240, matchService.findAll().size());
	}
	
}
