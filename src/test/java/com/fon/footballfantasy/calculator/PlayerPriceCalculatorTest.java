package com.fon.footballfantasy.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PlayerPriceCalculatorTest extends BaseCalculatorTest {

	@Autowired
	PlayerPriceCalculator priceCalculator;

	@Test
	void testGetUpdatedPrice_LessThanMinus2Points() {
		log.info("Calculating price for -3 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(-3).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(9.5, price);
	}
	
	@Test
	void testGetUpdatedPrice_Minus2Points() {
		log.info("Calculating price for -2 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(-2).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(9.5, price);
	}
	
	@Test
	void testGetUpdatedPrice_LessThan2Points() {
		log.info("Calculating price for 1 gw point");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(1).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(9.8, price);
	}
	
	@Test
	void testGetUpdatedPrice_2Points() {
		log.info("Calculating price for 2 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(2).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(10.0, price);
	}
	
	@Test
	void testGetUpdatedPrice_5Points() {
		log.info("Calculating price for 5 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(5).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(10.2, price);
	}
	
	@Test
	void testGetUpdatedPrice_MoreThan5Points() {
		log.info("Calculating price for 7 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(7).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(10.2, price);
	}
	
	@Test
	void testGetUpdatedPrice_10Points() {
		log.info("Calculating price for 10 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(10).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(10.5, price);
	}
	
	@Test
	void testGetUpdatedPrice_MoreThan10Points() {
		log.info("Calculating price for 15 gw points");
		Player player = Player.builder().name("Saša Zdjelar").price(10.0).build();
		PlayerGameweekPerformance pgp = PlayerGameweekPerformance.builder().player(player).points(15).build();
		double price = priceCalculator.getUpdatedPrice(pgp);
		assertEquals(10.5, price);
	}

}
