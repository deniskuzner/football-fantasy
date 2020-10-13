package com.fon.footballfantasy.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PlayerPerformanceCalculatorTest extends BaseCalculatorTest {

	@Autowired
	MatchPerformanceCalculator calculator;
	
	@Test
	void testGetMatchPerformances() {
		log.info("Calculating match performances");
		
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").club(club1).build();
		Player p2 = Player.builder().name("Umar Sadiq").position("FW").club(club1).build();
		Player p3 = Player.builder().name("Vladimir Stojkovic").position("GK").club(club1).build();
		Player p4 = Player.builder().name("Bojan Ostojic").position("DF").club(club1).build();
		club1.setPlayers(Arrays.asList(p1,p2,p3));
		Club club2 = Club.builder().id(2l)	.name("Vozdovac").url("/5379325a/Vozdovac-Stats").build();
		club2.setPlayers(Arrays.asList(p4));
		Match m1 = Match.builder().host(club1).guest(club2).result("2–0").build();
		
		Goal goal1 = Goal.builder().goalPlayer(p1).ownGoal(false).build();
		Goal goal2 = Goal.builder().goalPlayer(p1).ownGoal(false).build();
		Card yellow = Card.builder().player(p1).card("YELLOW").build();
		MinutesPlayed mp1 = MinutesPlayed.builder().player(p1).minutesPlayed(70).build();
		MinutesPlayed mp2 = MinutesPlayed.builder().player(p2).minutesPlayed(90).build();
		MinutesPlayed mp3 = MinutesPlayed.builder().player(p3).minutesPlayed(40).build();
		MinutesPlayed mp4 = MinutesPlayed.builder().player(p4).minutesPlayed(60).build();
		List<MatchEvent> events = Arrays.asList(goal1, goal2, yellow, mp1, mp2, mp3, mp4);
		m1.setEvents(events);
		
		List<PlayerGameweekPerformance> performances = calculator.getMatchPerformances(m1);
		assertNotNull(performances);
		assertEquals(4, performances.size());
		assertEquals(14, performances.stream().filter(p -> p.getPlayer().getName().equals("Saša Zdjelar")).findFirst().get().getPoints());
	}

}
