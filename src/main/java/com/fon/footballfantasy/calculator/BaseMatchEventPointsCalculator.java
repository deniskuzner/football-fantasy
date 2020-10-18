package com.fon.footballfantasy.calculator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.event.Card;
import com.fon.footballfantasy.domain.event.Goal;
import com.fon.footballfantasy.domain.event.MinutesPlayed;

@Component
public class BaseMatchEventPointsCalculator {
	
	int getGoalPoints(Goal goal) {
		int points = 0;
		Player goalPlayer = goal.getGoalPlayer();

		if (goal.isOwnGoal()) {
			points = -2;
			return points;
		}

		switch (goalPlayer.getPosition()) {
		case "GK":
			points = 6;
			break;
		case "DF":
			points = 6;
			break;
		case "MF":
			points = 5;
			break;
		case "FW":
			points = 4;
			break;
		}
		return points;
	}

	int getCardPoints(List<Card> cards) {
		int points = 0;
		List<Card> redCards = cards.stream().filter(c -> c.getCard().equals("RED")).collect(Collectors.toList());
		List<Card> yellowCards = cards.stream().filter(c -> c.getCard().equals("YELLOW")).collect(Collectors.toList());

		// red card or two yellow cards
		if(!redCards.isEmpty() || yellowCards.size() == 2) {
			points = -3;
		}
		// yellow card
		if(yellowCards.size() == 1) {
			points = -1;
		}
		
		return points;
	}

	int getMinutesPlayedPoints(MinutesPlayed minutesPlayed) {
		int points = 0;
		int minutes = minutesPlayed.getMinutesPlayed();
		if (minutes >= 60) {
			points = 2;
		} else if (minutes > 0) {
			points = 1;
		} else {
			points = 0;
		}
		return points;
	}


}
