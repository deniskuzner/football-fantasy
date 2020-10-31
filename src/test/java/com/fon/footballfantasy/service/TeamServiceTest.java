package com.fon.footballfantasy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamPlayer;
import com.fon.footballfantasy.exception.TeamException;
import com.fon.footballfantasy.repository.BaseRepositoryTest;
import com.fon.footballfantasy.setup.TeamSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TeamServiceTest extends BaseRepositoryTest {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamSetup teamSetup;
	
	Team team;
	
	@BeforeEach
	void beforeEach() {
		team = teamSetup.getFullValidSetup();
	}

	@Test
	void testSave_ValidTeam() {
		log.info("Test save valid team");
		assertNotNull(teamService.save(team));
	}

	@Test
	void testSave_InvalidTeamSize() {
		log.info("Test save team with invalid size");
		List<TeamPlayer> players = team.getTeamPlayers();
		players.remove(0);
		
		Exception exception = assertThrows(TeamException.class, () -> {
			teamService.save(team);
		});
		assertEquals("[TEAM_NOT_VALID] Invalid team size!", exception.getMessage());
	}
	
	@Test
	void testSave_InvalidTeamDistinctPlayers() {
		log.info("Test save team with duplicate players");
		List<TeamPlayer> players = team.getTeamPlayers();
		players.remove(0);
		players.add(players.get(0));
		
		Exception exception = assertThrows(TeamException.class, () -> {
			teamService.save(team);
		});
		assertEquals("[TEAM_NOT_VALID] Team contains duplicate players!", exception.getMessage());
	}
	
	@Test
	void testSave_InvalidTeamPositions() {
		log.info("Test save team with invalid positions");
		List<TeamPlayer> players = team.getTeamPlayers();
		TeamPlayer gk = players.stream().filter(tp -> tp.getPlayer().getPosition().equals("GK")).findFirst().get();
		gk.getPlayer().setPosition("FW");
		
		Exception exception = assertThrows(TeamException.class, () -> {
			teamService.save(team);
		});
		assertEquals("[TEAM_NOT_VALID] Invalid player positions!", exception.getMessage());
	}
	
	@Test
	void testSave_InvalidTeamBenchSize() {
		log.info("Test save team with invalid bench size");
		List<TeamPlayer> players = team.getTeamPlayers();
		TeamPlayer gk = players.stream().filter(tp -> tp.isOnBench() == true).findFirst().get();
		gk.setOnBench(false);
		
		Exception exception = assertThrows(TeamException.class, () -> {
			teamService.save(team);
		});
		assertEquals("[TEAM_NOT_VALID] Invalid bench size!", exception.getMessage());
	}
	
	@Test
	void testSave_InvalidTeamClubsLimit() {
		log.info("Test save team with invalid clubs limit");
		List<TeamPlayer> players = team.getTeamPlayers();
		players.get(0).getPlayer().setClub(players.get(10).getPlayer().getClub());
		
		Exception exception = assertThrows(TeamException.class, () -> {
			teamService.save(team);
		});
		assertEquals("[TEAM_NOT_VALID] You can select up to 3 players from a single club!", exception.getMessage());
	}
	
}
