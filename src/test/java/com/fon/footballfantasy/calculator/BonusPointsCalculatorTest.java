package com.fon.footballfantasy.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class BonusPointsCalculatorTest extends BaseCalculatorTest {
	
	@Autowired
	BonusPointsCalculator calculator;

	@Test
	void testCalculateWithoutSharingPlaces() {
		log.info("Calculating bonus points without sharing places");
		
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		PlayerGameweekPerformance pgp1 = PlayerGameweekPerformance.builder().points(2).build();
		performances.add(pgp1);
		PlayerGameweekPerformance pgp2 = PlayerGameweekPerformance.builder().points(1).build();
		performances.add(pgp2);
		PlayerGameweekPerformance pgp3 = PlayerGameweekPerformance.builder().points(5).build();
		performances.add(pgp3);
		
		Map<PlayerGameweekPerformance, Integer> bonus = calculator.calculate(performances);
		assertEquals(3, bonus.get(pgp3));
		assertEquals(2, bonus.get(pgp1));
		assertEquals(1, bonus.get(pgp2));
	}
	
	@Test
	void testCalculateSharingFirstPlace() {
		log.info("Calculating bonus points with sharing first place");
		
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		PlayerGameweekPerformance pgp1 = PlayerGameweekPerformance.builder().points(5).build();
		performances.add(pgp1);
		PlayerGameweekPerformance pgp2 = PlayerGameweekPerformance.builder().points(5).build();
		performances.add(pgp2);
		PlayerGameweekPerformance pgp3 = PlayerGameweekPerformance.builder().points(5).build();
		performances.add(pgp3);
		
		Map<PlayerGameweekPerformance, Integer> bonus = calculator.calculate(performances);
		assertEquals(3, bonus.get(pgp3));
		assertEquals(3, bonus.get(pgp1));
		assertEquals(3, bonus.get(pgp2));
		
		pgp3.setPoints(4);
		bonus = calculator.calculate(performances);
		assertEquals(2, bonus.get(pgp3));
		assertEquals(3, bonus.get(pgp1));
		assertEquals(3, bonus.get(pgp2));
	}

	@Test
	void testCalculateSharingSecondPlace() {
		log.info("Calculating bonus points with sharing second place");
		
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		PlayerGameweekPerformance pgp1 = PlayerGameweekPerformance.builder().points(2).build();
		performances.add(pgp1);
		PlayerGameweekPerformance pgp2 = PlayerGameweekPerformance.builder().points(1).build();
		performances.add(pgp2);
		PlayerGameweekPerformance pgp3 = PlayerGameweekPerformance.builder().points(1).build();
		performances.add(pgp3);
		
		Map<PlayerGameweekPerformance, Integer> bonus = calculator.calculate(performances);
		assertEquals(2, bonus.get(pgp3));
		assertEquals(3, bonus.get(pgp1));
		assertEquals(2, bonus.get(pgp2));
	}
	
	@Test
	void testCalculateSharingThirdPlace() {
		log.info("Calculating bonus points with sharing third place");
		
		List<PlayerGameweekPerformance> performances = new ArrayList<>();
		PlayerGameweekPerformance pgp1 = PlayerGameweekPerformance.builder().points(1).build();
		performances.add(pgp1);
		PlayerGameweekPerformance pgp2 = PlayerGameweekPerformance.builder().points(3).build();
		performances.add(pgp2);
		PlayerGameweekPerformance pgp3 = PlayerGameweekPerformance.builder().points(4).build();
		performances.add(pgp3);
		PlayerGameweekPerformance pgp4 = PlayerGameweekPerformance.builder().points(1).build();
		performances.add(pgp4);
		
		Map<PlayerGameweekPerformance, Integer> bonus = calculator.calculate(performances);
		assertEquals(1, bonus.get(pgp1));
		assertEquals(2, bonus.get(pgp2));
		assertEquals(3, bonus.get(pgp3));
		assertEquals(1, bonus.get(pgp4));
	}
	
}
