package com.fon.footballfantasy.calculator;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Player;

@Component
public class BaseMatchEventPointsCalculator {
	
	int getGoalPoints(Goal goal) {
		int points = 0;
		Player goalPlayer = goal.getGoalPlayer();

		if (goal.isOwnGoal()) {
			points = -2;
			return points;
		}

		// TODO: razmisliti kako da se rese duple pozicije igraca

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

	int getCardPoints(Card card) {
		int points = 0;
		switch (card.getCard()) {
		case "YELLOW":
			points = -1;
			break;
		case "RED":
			points = -3;
			break;
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
