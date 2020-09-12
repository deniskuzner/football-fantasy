package com.fon.footballfantasy.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

@Component
public class PlayerPerformanceCalculator {

	public List<PlayerGameweekPerformance> getMatchPerformances(Match match) {
		List<PlayerGameweekPerformance> performances = initializeMatchPerformances(match);
		List<MatchEvent> matchEvents = match.getEvents();
		for (MatchEvent matchEvent : matchEvents) {
			updatePerformances(matchEvent, performances);
		}

		// proci kroz listu i obraditi clean sheet situaciju
		calculateCleanSheetPoints(match, performances);

		//TODO obraditi skidanje poena igracima za primljen gol u zavisnosti od toga da li
		// su bili u igri
		
		//TODO skinuti golmanu i odbrani za svaki drugi primljen gol

		//TODO na kraju proci kroz listu i izracunati bonus

		return performances;
	}

	private List<PlayerGameweekPerformance> initializeMatchPerformances(Match match) {
		List<PlayerGameweekPerformance> initialPerformances = new ArrayList<>();
		Gameweek gameweek = match.getGameweek();
		// Host club players
		for (Player p : match.getHost().getPlayers()) {
			initialPerformances.add(PlayerGameweekPerformance.builder().player(p).gameweek(gameweek).points(0).build());
		}

		// Guest club players
		for (Player p : match.getGuest().getPlayers()) {
			initialPerformances.add(PlayerGameweekPerformance.builder().player(p).gameweek(gameweek).points(0).build());
		}

		return initialPerformances;
	}

	// POKRIVA POENE ZA: DAT GOL, KARTON, ODIGRANE MINUTE
	private void updatePerformances(MatchEvent matchEvent, List<PlayerGameweekPerformance> performances) {
		int points = 0;

		// Goal
		if (matchEvent instanceof Goal) {
			Goal goal = (Goal) matchEvent;
			points = getGoalPoints(goal);

			PlayerGameweekPerformance pgp = performances.stream()
					.filter(p -> p.getPlayer().getId() == goal.getGoalPlayer().getId()).findFirst().get();
			pgp.setPoints(pgp.getPoints() + points);
		}

		// Card
		if (matchEvent instanceof Card) {
			Card card = (Card) matchEvent;
			points = getCardPoints(card);

			PlayerGameweekPerformance pgp = performances.stream()
					.filter(p -> p.getPlayer().getId() == card.getPlayer().getId()).findFirst().get();
			pgp.setPoints(pgp.getPoints() + points);
		}

		// Minutes played
		if (matchEvent instanceof MinutesPlayed) {
			MinutesPlayed minutesPlayed = (MinutesPlayed) matchEvent;
			points = getMinutesPlayedPoints(minutesPlayed);

			PlayerGameweekPerformance pgp = performances.stream()
					.filter(p -> p.getPlayer().getId() == minutesPlayed.getPlayer().getId()).findFirst().get();
			pgp.setPoints(pgp.getPoints() + points);
		}

	}
	
	// CLEAN SHEET POINTS
	//TODO UZETI U OBZIR SAMO IGRACE KOJI SU IGRALI VISE OD 0 MINUTA!!!
	private void calculateCleanSheetPoints(Match match, List<PlayerGameweekPerformance> performances) {
		String result = match.getResult();
		List<PlayerGameweekPerformance> cleanSheetTeamPerformances = new ArrayList<>();

		// Host clean sheet
		if (result.substring(0, result.indexOf("-")).equals("0")) {
			cleanSheetTeamPerformances = performances.stream().filter(p -> p.isHost(match, p.getPlayer().getClub()))
					.collect(Collectors.toList());
		}

		// Guest clean sheet
		if (result.substring(result.indexOf("-") + 1).equals("0")) {
			cleanSheetTeamPerformances = performances.stream().filter(p -> p.isGuest(match, p.getPlayer().getClub()))
					.collect(Collectors.toList());
		}

		if (!cleanSheetTeamPerformances.isEmpty()) {
			List<PlayerGameweekPerformance> gkAndDf = cleanSheetTeamPerformances.stream()
					.filter(p -> p.getPlayer().getPosition().equals("GK") || p.getPlayer().getPosition().equals("DF"))
					.collect(Collectors.toList());
			List<PlayerGameweekPerformance> mf = cleanSheetTeamPerformances.stream()
					.filter(p -> p.getPlayer().getPosition().equals("MF")).collect(Collectors.toList());

			for (PlayerGameweekPerformance pgp : gkAndDf) {
				pgp.setPoints(pgp.getPoints() + 4);
			}
			for (PlayerGameweekPerformance pgp : mf) {
				pgp.setPoints(pgp.getPoints() + 1);
			}
		}

	}

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
