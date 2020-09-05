package com.fon.footballfantasy.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Card;
import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Goal;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.domain.MinutesPlayed;
import com.fon.footballfantasy.domain.Substitution;
import com.fon.footballfantasy.repository.MatchEventRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MatchEventSetup {

	@Autowired
	MatchEventRepository matchEventRepository;

	@Autowired
	GameweekSetup gameweekSetup;

	public List<MatchEvent> getSetup() {
		Gameweek gameweek = gameweekSetup.getSetup();
		Match match = gameweek.getMatches().get(0);
		Club club = match.getHost();
		List<MatchEvent> setup = new ArrayList<>();

		setup.add(Goal.builder().matchId(match.getId()).minute("2").club(club).goalPlayer(club.getPlayers().get(0)).build());
		setup.add(Card.builder().matchId(match.getId()).minute("4").club(club).player(club.getPlayers().get(0)).card("red").build());
		setup.add(MinutesPlayed.builder().matchId(match.getId()).minute("7").club(club).player(club.getPlayers().get(0))
				.minutesPlayed(7).build());
		setup.add(Substitution.builder().matchId(match.getId()).minute("42").club(club).inPlayer(club.getPlayers().get(0))
				.outPlayer(club.getPlayers().get(1)).build());
		
		log.info("Saving match events");
		setup.forEach(event -> {
			matchEventRepository.save(event);
		});

		return setup;
	}

}
