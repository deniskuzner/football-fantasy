package com.fon.footballfantasy.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Player;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class BaseMatchEventPointsCalculatorTest extends BaseCalculatorTest {

	@Autowired
	BaseMatchEventPointsCalculator calculator;

	@Test
	void testGetGoalPoints() {
		log.info("Get goal points");

		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").build();
		Player p2 = Player.builder().name("Umar Sadiq").position("FW").build();
		Player p3 = Player.builder().name("Vladimir Stojkovic").position("GK").build();
		Player p4 = Player.builder().name("Bojan Ostojic").position("DF").build();
		
		Goal goal1 = Goal.builder().goalPlayer(p1).ownGoal(false).build();
		Goal goal2 = Goal.builder().goalPlayer(p2).ownGoal(false).build();
		Goal goal3 = Goal.builder().goalPlayer(p3).ownGoal(false).build();
		Goal goal4 = Goal.builder().goalPlayer(p4).ownGoal(false).build();
		Goal ownGoal = Goal.builder().goalPlayer(p2).ownGoal(true).build();
		
		assertEquals(5, calculator.getGoalPoints(goal1));
		assertEquals(4, calculator.getGoalPoints(goal2));
		assertEquals(6, calculator.getGoalPoints(goal3));
		assertEquals(6, calculator.getGoalPoints(goal4));
		assertEquals(-2, calculator.getGoalPoints(ownGoal));
	}

	@Test
	void testGetCardPoints() {
		log.info("Get card points");

		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").build();
		Card yellow = Card.builder().player(p1).card("YELLOW").build();
		Card red = Card.builder().player(p1).card("RED").build();
		
		assertEquals(-1, calculator.getCardPoints(yellow));
		assertEquals(-3, calculator.getCardPoints(red));
	}

	@Test
	void testGetMinutesPlayedPoints() {
		log.info("Get minutes played points");
		
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").build();
		MinutesPlayed mp1 = MinutesPlayed.builder().player(p1).minutesPlayed(70).build();
		MinutesPlayed mp2 = MinutesPlayed.builder().player(p1).minutesPlayed(45).build();
		MinutesPlayed mp3 = MinutesPlayed.builder().player(p1).minutesPlayed(0).build();
		MinutesPlayed mp4 = MinutesPlayed.builder().player(p1).minutesPlayed(60).build();
		
		assertEquals(2, calculator.getMinutesPlayedPoints(mp1));
		assertEquals(2, calculator.getMinutesPlayedPoints(mp4));
		assertEquals(1, calculator.getMinutesPlayedPoints(mp2));
		assertEquals(0, calculator.getMinutesPlayedPoints(mp3));
	}

}
