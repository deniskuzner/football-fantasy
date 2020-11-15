package com.fon.footballfantasy.calculator;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

@Component
public class PlayerPriceCalculator {

	public double getBasePrice(Player player) {
		double price = 0;
		String position = player.getPosition();
		
		if(position.equals("GK") || position.equals("DF")) {
			price = 5.6;
		} else if(position.equals("MF") || position.equals("FW")) {
			price = 7.6;
		}
		return price;
	}

	public double getUpdatedPrice(PlayerGameweekPerformance pgp) {
		Player player = pgp.getPlayer();
		double price = player.getPrice();
		int gameweekPoints = pgp.getPoints();
		
		if(gameweekPoints <= -2) {
			price = price - 0.5;
		} else if(gameweekPoints < 2) {
			price = price - 0.2;
		} else if(gameweekPoints >= 5 && gameweekPoints < 10) {
			price = price + 0.2;
		} else if(gameweekPoints >= 10) {
			price = price + 0.5;
		}
		
		// min price
		if(price < 3.5) {
			price = 3.5;
		}
		// max price
		if(price > 15) {
			price = 15;
		}
		
		return price;
	}

}
