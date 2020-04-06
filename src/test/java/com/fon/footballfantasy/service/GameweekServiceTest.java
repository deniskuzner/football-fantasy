package com.fon.footballfantasy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.repository.BaseRepositoryTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GameweekServiceTest extends BaseRepositoryTest {
	
	@Autowired
	GameweekService gameweekService;

	@Test
	void test() {
		gameweekService.parseSeasonGameweeks();
	}

}
