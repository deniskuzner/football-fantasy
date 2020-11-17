package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.setup.TeamSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TeamRepositoryTest extends BaseRepositoryTest {

	@Autowired
	TeamRepository userTeamRepository;

	@Autowired
	TeamSetup teamSetup;

	@Test
	void testCrud() {

		log.info("Getting team setup");
		Team team = teamSetup.getSetup();

		log.info("Find user team by id");
		Team t = userTeamRepository.findById(team.getId()).get();
		assertNotNull(t);
		assertEquals(team.getName(), t.getName());
		assertEquals(team.getTotalPoints(), t.getTotalPoints());
		assertEquals(team.getFreeTransfers(), t.getFreeTransfers());
		assertEquals(team.getUser().getId(), t.getUser().getId());
		assertEquals(team.getCaptainId(), t.getCaptainId());
		assertEquals(team.getViceCaptainId(), t.getViceCaptainId());
		assertEquals(team.getTeamPlayers().size(), t.getTeamPlayers().size());
		assertEquals(team.getTeamGameweekPerformances().size(), t.getTeamGameweekPerformances().size());

	}

	@AfterEach
	void deleteAll() {
		log.info("Deleting all");
		teamSetup.deleteAll();
		assertEquals(0, ((List<Team>) userTeamRepository.findAll()).size());
	}

}
