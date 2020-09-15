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
class CleanSheetPointsCalculatorTest extends BaseCalculatorTest {
	
	@Autowired
	CleanSheetPointsCalculator calculator;

	@Test
	void testNotCleanSheetCandidate() {
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").manager("Aleksandar Stanojevic").build();
		Player p1 = Player.builder().name("Saša Zdjelar").club(club1).build();
		Club club2 = Club.builder().id(2l)	.name("Vozdovac").url("/5379325a/Vozdovac-Stats").manager("Jovan Damjanović").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("2–0").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(0).minuteOut(30)
				.inSubstitution(Optional.empty())
				.outSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("0:0").build())).build();
		
		log.info("Not clean sheet candidate test");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testFinalResultCleanSheet() {
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").manager("Aleksandar Stanojevic").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").manager("Jovan Damjanović").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("0–2").build();

		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(0).minuteOut(90)
				.inSubstitution(Optional.empty())
				.outSubstitution(Optional.empty()).build();

		log.info("Player's team conceded goal test");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("MF player clean sheet test");
		p1.setClub(club2);
		assertEquals(1, calculator.calculate(p1, m1, mpd));
		
		log.info("GK player clean sheet test");
		p1.setPosition("GK");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
		
		log.info("DF player clean sheet test");
		p1.setPosition("DF");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testPlayedFromStartAndSubstitutedLater() {
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").manager("Aleksandar Stanojevic").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").manager("Jovan Damjanović").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("1–2").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(0).minuteOut(70)
				.inSubstitution(Optional.empty())
				.outSubstitution(Optional.of(Substitution.builder().outPlayer(p1).result("0:1").build())).build();
		
		log.info("Player's team conceded goal test");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("MF player clean sheet test");
		p1.setClub(club2);
		assertEquals(1, calculator.calculate(p1, m1, mpd));
		
		log.info("GK player clean sheet test");
		p1.setPosition("GK");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
		
		log.info("DF player clean sheet test");
		p1.setPosition("DF");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testEnteredFromBenchAndNotSubstituted() {
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").manager("Aleksandar Stanojevic").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").manager("Jovan Damjanović").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("2–3").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(25).minuteOut(90)
				.inSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("1:1").build()))
				.outSubstitution(Optional.empty()).build();
		
		log.info("Player's team conceded goal test");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("MF player clean sheet test");
		p1.setClub(club2);
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		m1.setResult("1–3");
		assertEquals(1, calculator.calculate(p1, m1, mpd));
		
		log.info("GK player clean sheet test");
		p1.setPosition("GK");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
		
		log.info("DF player clean sheet test");
		p1.setPosition("DF");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
	}
	
	@Test
	void testEnteredFromBenchAndSubstitutedLater() {
		Club club1 = Club.builder().id(1l).name("Partizan").url("/dde3e804/Partizan").manager("Aleksandar Stanojevic").build();
		Player p1 = Player.builder().name("Saša Zdjelar").position("MF").club(club1).build();
		Club club2 = Club.builder().id(2l).name("Vozdovac").url("/5379325a/Vozdovac-Stats").manager("Jovan Damjanović").build();
		Match m1 = Match.builder().host(club1).guest(club2).result("2–3").build();
		
		MinutesPlayedDetails mpd = MinutesPlayedDetails.builder().minuteIn(25).minuteOut(87)
				.inSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("1:1").build()))
				.outSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("2:2").build())).build();
		
		log.info("Player's team conceded goal test");
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		log.info("MF player clean sheet test");
		p1.setClub(club2);
		assertEquals(0, calculator.calculate(p1, m1, mpd));
		
		mpd.setOutSubstitution(Optional.of(Substitution.builder().inPlayer(p1).result("1:2").build()));
		assertEquals(1, calculator.calculate(p1, m1, mpd));
		
		log.info("GK player clean sheet test");
		p1.setPosition("GK");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
		
		log.info("DF player clean sheet test");
		p1.setPosition("DF");
		assertEquals(4, calculator.calculate(p1, m1, mpd));
	}
	
}
