package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.setup.TeamSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TeamPlayerRepositoryTest extends BaseRepositoryTest {

	@Autowired
	TeamPlayerRepository teamPlayerRepository;

	@Autowired
	TeamSetup teamSetup;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Test
	void testDeleteByTeamId() {
		
		Team team = teamSetup.getSetup();
		assertTrue(team.getTeamPlayers().size() > 0);
		
		log.info("Test delete by team id");
		long deletedRecords = teamPlayerRepository.deleteByTeamId(team.getId());
		assertEquals(team.getTeamPlayers().size(), deletedRecords);
		
	}

	@AfterEach
	void deleteAll() {
		log.info("Deleting all");
		teamSetup.deleteAll();
	}
	
}
