package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Substitution;
import com.fon.footballfantasy.setup.MatchEventSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class MatchEventRepositoryTest extends BaseRepositoryTest {

	@Autowired
	MatchEventRepository matchEventRepository;
	
	@Autowired
	MatchEventSetup matchEventSetup;
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	GameweekRepository gameweekRepository;
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	ClubRepository clubRepository;
	
	@Test
	public void testCrud() {
		
		log.info("Getting setup");
		List<MatchEvent> matchEvents = matchEventSetup.getSetup();
		MatchEvent goal = matchEvents.get(0);
		MatchEvent card = matchEvents.get(1);
		MatchEvent minutesPlayed = matchEvents.get(2);
		MatchEvent substitution = matchEvents.get(3);
		
		
		log.info("Getting all match events");
		List<MatchEvent> allMatchEvents = (List<MatchEvent>) matchEventRepository.findAll();
		assertEquals(4, allMatchEvents.size());
		
		log.info("Goal event");
		MatchEvent me1 = matchEventRepository.findById(goal.getId()).get();
		assertNotNull(me1);
		assertEquals(me1.getClass(), Goal.class);
		assertEquals(me1.getMatchId(), goal.getMatchId());
		assertEquals(me1.getMinute(), goal.getMinute());
		assertNotNull(me1.getClub());
		assertEquals(((Goal) me1).getGoalPlayer().getId(), ((Goal) goal).getGoalPlayer().getId());
		
		log.info("Card event");
		MatchEvent me2 = matchEventRepository.findById(card.getId()).get();
		assertNotNull(me2);
		assertEquals(me2.getClass(), Card.class);
		assertEquals(me2.getMatchId(), card.getMatchId());
		assertEquals(me2.getMinute(), card.getMinute());
		assertNotNull(me2.getClub());
		assertEquals(((Card) me2).getPlayer().getId(), ((Card) card).getPlayer().getId());
		assertEquals(((Card) me2).getCard(), ((Card) card).getCard());
		
		log.info("Minutes played event");
		MatchEvent me3 = matchEventRepository.findById(minutesPlayed.getId()).get();
		assertNotNull(me3);
		assertEquals(me3.getClass(), MinutesPlayed.class);
		assertEquals(me3.getMatchId(), minutesPlayed.getMatchId());
		assertEquals(me3.getMinute(), minutesPlayed.getMinute());
		assertNotNull(me3.getClub());
		assertEquals(((MinutesPlayed) me3).getPlayer().getId(), ((MinutesPlayed) minutesPlayed).getPlayer().getId());
		assertEquals(((MinutesPlayed) me3).getMinutesPlayed(), ((MinutesPlayed) minutesPlayed).getMinutesPlayed());
		
		log.info("Substitution event");
		MatchEvent me4 = matchEventRepository.findById(substitution.getId()).get();
		assertNotNull(me4);
		assertEquals(me4.getClass(), Substitution.class);
		assertEquals(me4.getMatchId(), substitution.getMatchId());
		assertEquals(me4.getMinute(), substitution.getMinute());
		assertNotNull(me4.getClub());
		assertEquals(((Substitution) me4).getInPlayer().getId(), ((Substitution) substitution).getInPlayer().getId());
		assertEquals(((Substitution) me4).getOutPlayer().getId(), ((Substitution) substitution).getOutPlayer().getId());
		
	}
	
	@AfterEach
	public void deleteAll() {
		matchEventRepository.deleteAll();
		assertEquals(0, ((List<MatchEvent>) matchEventRepository.findAll()).size());
		
		log.info("Deleting everything");
		
		playerRepository.deleteAll();
		matchRepository.deleteAll();
		clubRepository.deleteAll();
		gameweekRepository.deleteAll();
	}

}
