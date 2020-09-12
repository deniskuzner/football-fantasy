package com.fon.footballfantasy.setup;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.repository.GameweekRepository;
import com.fon.footballfantasy.repository.MatchRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameweekSetup {
	
	@Autowired
	GameweekRepository gameweekRepository;
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	ClubSetup clubSetup;
	
	public Gameweek getSetup() {
		
		List<Club> clubs = clubSetup.getSetup();
		Club host = clubs.get(0);
		Club guest = clubs.get(1);

		Gameweek gameweek = Gameweek.builder().orderNumber(1).build();
		Match m1 = Match.builder().dateTime(LocalDateTime.parse("2020-07-12T20:00"))
				.gameweek(gameweek).host(host).guest(guest).result("2:0")
				.url("/b6d88aea/Vozdovac-TSC-Backa-Top-July-12-2019-Serbian-SuperLiga").venue("JNA").build();
		Match m2 = Match.builder().dateTime(LocalDateTime.parse("2020-07-19T21:00"))
				.gameweek(gameweek).host(guest).guest(host).result("1:2")
				.url("/b6d88aea/Vozdovac-TSC-Backa-Top-July-19-2019-Serbian-SuperLiga").venue("JNA").build();
		gameweek.setMatches(Arrays.asList(m1,m2));
		
		log.info("Saving gameweek");
		gameweek = gameweekRepository.save(gameweek);
		log.info("Saving gameweek matches");
		gameweek.getMatches().forEach(m -> matchRepository.save(m));
		
		return gameweek;
		
	}

}
