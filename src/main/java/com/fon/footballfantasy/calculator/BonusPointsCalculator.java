package com.fon.footballfantasy.calculator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

@Component
public class BonusPointsCalculator {
	
	public void updateBonusPoints(List<PlayerGameweekPerformance> performances) {
		List<PlayerGameweekPerformance> sortedPerformances = performances.stream()
				.sorted(Comparator.comparing(PlayerGameweekPerformance::getPoints).reversed())
				.collect(Collectors.toList());
		
		// Niko ne deli mesto
		if(sortedPerformances.get(0).getPoints() > sortedPerformances.get(1).getPoints() 
				&& sortedPerformances.get(1).getPoints() > sortedPerformances.get(2).getPoints()) {
			sortedPerformances.get(0).setPoints(sortedPerformances.get(0).getPoints() + 3);
			sortedPerformances.get(1).setPoints(sortedPerformances.get(1).getPoints() + 2);
			sortedPerformances.get(2).setPoints(sortedPerformances.get(2).getPoints() + 1);
		}
		
		// deli se prvo mesto
		
		// deli se drugo mesto
		
		// deli se trece mesto
	}

}
