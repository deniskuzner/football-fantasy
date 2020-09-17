package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.setup.GameweekSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class MatchRepositoryTest extends BaseRepositoryTest {

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	GameweekRepository gameweekRepository;

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	GameweekSetup gameweekSetup;

	@Test
	void testSearchMatches() {
		log.info("Search matches");

		Gameweek gameweek = gameweekSetup.getSetup();
		List<Match> setupMatches = gameweek.getMatches();
		Match m1 = setupMatches.get(0);

		List<Match> matches = matchRepository.findBySentAndDateTimeBetween(false, m1.getDateTime().minusDays(1),
				m1.getDateTime().plusDays(1));

		assertNotNull(matches);
		assertEquals(1, matches.size());
		assertEquals(m1.getId(), matches.get(0).getId());
		assertEquals(m1.getDateTime(), matches.get(0).getDateTime());
		assertEquals(m1.getHost().getId(), matches.get(0).getHost().getId());
		assertEquals(m1.getGuest().getId(), matches.get(0).getGuest().getId());
	}

	@Test
	void testFindByGameweekId() {
		log.info("Find matches by gameweekId");

		Gameweek gameweek = gameweekSetup.getSetup();
		List<Match> setupMatches = gameweek.getMatches();
		Match m1 = setupMatches.get(0);

		List<Match> matches = matchRepository.findBySentAndGameweekId(false, gameweek.getId());

		assertNotNull(matches);
		assertEquals(2, matches.size());
		assertEquals(m1.getId(), matches.get(0).getId());
		assertEquals(m1.getDateTime(), matches.get(0).getDateTime());
		assertEquals(m1.getHost().getId(), matches.get(0).getHost().getId());
		assertEquals(m1.getGuest().getId(), matches.get(0).getGuest().getId());
	}

	@AfterEach
	void deleteAll() {
		log.info("Deleting everything");
		gameweekRepository.deleteAll();
		clubRepository.deleteAll();
		assertEquals(0, ((List<Match>) matchRepository.findAll()).size());
	}

}
