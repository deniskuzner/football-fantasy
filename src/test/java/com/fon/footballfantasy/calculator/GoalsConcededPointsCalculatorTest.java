package com.fon.footballfantasy.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.Substitution;
import com.fon.footballfantasy.service.dto.MinutesPlayedDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GoalsConcededPointsCalculatorTest extends BaseCalculatorTest {
	
	@Autowired
	GoalsConcededPointsCalculator calculator;

	@Test
	void testMFAndFWPlayers() {
		log.info("Test MF and FW players");
	
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("2–3").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(0).minuteOut(0)
				.inSubstitution(Optional.empty()).outSubstitution(Optional.empty()).build();
		
		assertEquals(0, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testPlayed90Minutes() {
		log.info("Test played 90 minutes");
	
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("DF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("0–0").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(0).minuteOut(90)
				.inSubstitution(Optional.empty()).outSubstitution(Optional.empty()).build();
		
		log.info("0 goals conceded");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("3 goals conceded");
		m1.setResult("0–3");
		assertEquals(-1, calculator.calculate(p1, m1, mpd));
		
		log.info("4 goals conceded");
		m1.setResult("0–4");
		assertEquals(-2, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testPlayedFromStartAndSubstitutedLater() {
		log.info("Test played from start and substituted later");
		
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("DF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("0–7").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(0).minuteOut(70)
				.inSubstitution(Optional.empty())
				.outSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("0:1").build())).build();
		
		log.info("1 goal conceded");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("3 goals conceded");
		mpd.setOutSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("0:3").build()));
		assertEquals(-1, calculator.calculate(p1, m1, mpd));
		
		log.info("7 goals conceded");
		mpd.setOutSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("0:7").build()));
		assertEquals(-3, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testEnteredFromBenchAndNotSubstituted() {
		log.info("Test entered from bench and not substituted later");
		
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("DF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("1–1").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(25).minuteOut(90)
				.inSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("1:1").build()))
				.outSubstitution(Optional.empty()).build();
		
		log.info("0 goal conceded");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("2 goals conceded");
		m1.setResult("1–2");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("3 goals conceded");
		m1.setResult("1–3");
		assertEquals(-1, calculator.calculate(p1, m1, mpd));
		
		log.info("5 goals conceded");
		m1.setResult("1–5");
		assertEquals(-2, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testEnteredFromBenchAndSubstitutedLater() {
		log.info("Test entered from bench and substituted later");
		
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("DF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("1–6").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(25).minuteOut(90)
				.inSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("1:1").build()))
				.outSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("2:1").build())).build();
		
		log.info("0 goal conceded");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("2 goals conceded");
		mpd.setOutSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("1:2").build()));
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("3 goals conceded");
		mpd.setOutSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("1:3").build()));
		assertEquals(-1, calculator.calculate(p1, m1, mpd));
		
		log.info("5 goals conceded");
		mpd.setOutSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("1:5").build()));
		assertEquals(-2, calculator.calculate(p1, m1, mpd));
	}

}
