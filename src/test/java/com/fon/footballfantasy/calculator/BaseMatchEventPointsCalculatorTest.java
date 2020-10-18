package com.fon.footballfantasy.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.event.Card;
import com.fon.footballfantasy.domain.event.Goal;
import com.fon.footballfantasy.domain.event.MinutesPlayed;

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
		Card yellow1 = Card.builder().player(p1).card("YELLOW").build();
		Card yellow2 = Card.builder().player(p1).card("YELLOW").build();
		Card red = Card.builder().player(p1).card("RED").build();
		List<Card> cards = new ArrayList<>();
		
		log.info("No cards");
		assertEquals(0, calculator.getCardPoints(cards));
		
		log.info("Single yellow card");
		cards.add(yellow1);
		assertEquals(-1, calculator.getCardPoints(cards));
		log.info("Two yellow cards");
		cards.add(yellow2);
		assertEquals(-3, calculator.getCardPoints(cards));
		log.info("Yellow and then Red card");
		cards.add(red);
		assertEquals(-3, calculator.getCardPoints(cards));
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
