package com.fon.footballfantasy.calculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.domain.Substitution;
import com.fon.footballfantasy.service.dto.MinutesPlayedDetails;

@Component
public class PlayerPerformanceCalculator {

	@Autowired
	BaseMatchEventPointsCalculator baseEventPointsCalculator;
	
	@Autowired
	CleanSheetPointsCalculator cleanSheetPointsCalculator;
	
	@Autowired
	GoalsConcededPointsCalculator goalsConcededPointsCalculator;
	
	@Autowired
	BonusPointsCalculator bonusPointsCalculator;

	private Match match;
	private List<MatchEvent> matchEvents;
	private List<MinutesPlayed> minutesPlayed;
	private List<Goal> goals;
	private List<Card> cards;
	private List<Substitution> substitutions;

	public List<PlayerGameweekPerformance> getMatchPerformances(Match match) {
		this.match = match;
		this.matchEvents = match.getEvents();
		this.minutesPlayed = matchEvents.stream().filter(event -> event instanceof MinutesPlayed)
				.map(event -> (MinutesPlayed) event).collect(Collectors.toList());
		this.goals = matchEvents.stream().filter(event -> event instanceof Goal).map(event -> (Goal) event)
				.collect(Collectors.toList());
		this.cards = matchEvents.stream().filter(event -> event instanceof Card).map(event -> (Card) event)
				.collect(Collectors.toList());
		this.substitutions = matchEvents.stream().filter(event -> event instanceof Substitution)
				.map(event -> (Substitution) event).collect(Collectors.toList());

		List<PlayerGameweekPerformance> performances = initializeMatchPerformances(match);

		for (PlayerGameweekPerformance playerGameweekPerformance : performances) {
			updatePlayerGameweekPerformance(playerGameweekPerformance);
		}

		updateBonusPoints(performances);

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

	// RACUNA POENE ZA: DAT GOL, AUTO GOL, KARTONE, ODIGRANE MINUTE, CLEAN SHEET, PRIMLJENE GOLOVE
	private void updatePlayerGameweekPerformance(PlayerGameweekPerformance pgp) {
		Player player = pgp.getPlayer();

		Optional<MinutesPlayed> playerMinutesPlayed = minutesPlayed.stream()
				.filter(mp -> mp.getPlayer().getId() == player.getId()).findFirst();
		// Ako igrac nije igrao na mecu, preskociti ga
		if (!playerMinutesPlayed.isPresent()) {
			return;
		}

		// Getting concrete player events
		List<Goal> playerGoals = goals.stream().filter(g -> g.getGoalPlayer().getId() == player.getId())
				.collect(Collectors.toList());
		List<Card> playerCards = cards.stream().filter(c -> c.getPlayer().getId() == player.getId())
				.collect(Collectors.toList());
		List<Substitution> playerSubstitutions = substitutions.stream()
				.filter(s -> s.getInPlayer().getId() == player.getId() || s.getOutPlayer().getId() == player.getId())
				.collect(Collectors.toList());

		// Calculating base event points
		int points = 0;
		points += baseEventPointsCalculator.getMinutesPlayedPoints(playerMinutesPlayed.get());
		for (Goal goal : playerGoals) {
			points += baseEventPointsCalculator.getGoalPoints(goal);
		}
		for (Card card : playerCards) {
			points += baseEventPointsCalculator.getCardPoints(card);
		}
		
		MinutesPlayedDetails mpDetails = getMinutesPlayedDetails(pgp, playerSubstitutions);

		// Calculate goalkeeper or defender clean sheet points
		points += cleanSheetPointsCalculator.calculate(pgp.getPlayer(), match, mpDetails);
		
		// Calculate goals conceded by a goalkeeper or defender points
		if(!match.getResult().equals("0-0")) {
			points += goalsConcededPointsCalculator.calculate(pgp.getPlayer(), match, mpDetails);
		}

		pgp.setPoints(points);
	}
	
	private MinutesPlayedDetails getMinutesPlayedDetails(PlayerGameweekPerformance pgp, List<Substitution> playerSubstitutions) {
		Player player = pgp.getPlayer();
		int minuteIn = 0;
		int minuteOut = 90;
		Optional<Substitution> inSubstitution = Optional.empty();
		Optional<Substitution> outSubstitution = Optional.empty();
		// Ako nema izmene, igrao je od pocetka do kraja
		if (!playerSubstitutions.isEmpty()) {
			inSubstitution = playerSubstitutions.stream().filter(s -> s.getInPlayer().getId() == player.getId())
					.findFirst();
			outSubstitution = playerSubstitutions.stream().filter(s -> s.getOutPlayer().getId() == player.getId())
					.findFirst();

			if (inSubstitution.isPresent()) {
				minuteIn = parseMinute(inSubstitution.get().getMinute());
			}
			if (outSubstitution.isPresent()) {
				minuteOut = parseMinute(outSubstitution.get().getMinute());
			}
		}
		return MinutesPlayedDetails.builder().minuteIn(minuteIn).minuteOut(minuteOut).inSubstitution(inSubstitution)
				.outSubstitution(outSubstitution).build();
	}

	private int parseMinute(String fullMinuteString) {
		int result = 0;
		String minute = fullMinuteString.replace("'", "");
		if (minute.contains("+")) {
			String[] minutes = minute.split("+");
			result = Integer.parseInt(minutes[0]) + Integer.parseInt(minutes[1]);
			return result;
		}
		return Integer.parseInt(minute);
	}
	
	private void updateBonusPoints(List<PlayerGameweekPerformance> performances) {
		Map<PlayerGameweekPerformance, Integer> bonus = bonusPointsCalculator.calculate(performances);
		Iterator it = bonus.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry pair = (Entry)it.next();
	        PlayerGameweekPerformance pgp = (PlayerGameweekPerformance) pair.getKey();
	        int bonusPoints = (int) pair.getValue();
	        pgp.setPoints(pgp.getPoints() + bonusPoints);
	        it.remove();
	    }
		
	}

}
