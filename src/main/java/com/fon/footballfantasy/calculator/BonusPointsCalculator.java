package com.fon.footballfantasy.calculator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

@Component
public class BonusPointsCalculator {
	
	public Map<PlayerGameweekPerformance, Integer> calculate(List<PlayerGameweekPerformance> performances) {
		Map<PlayerGameweekPerformance, Integer> bonusMap = new HashMap<>();
		
		List<PlayerGameweekPerformance> sortedPerformances = performances.stream()
				.sorted(Comparator.comparing(PlayerGameweekPerformance::getPoints).reversed())
				.collect(Collectors.toList());
		
		// Niko ne deli mesto
		if(sortedPerformances.get(0).getPoints() > sortedPerformances.get(1).getPoints() 
				&& sortedPerformances.get(1).getPoints() > sortedPerformances.get(2).getPoints()) {
			bonusMap.put(sortedPerformances.get(0), 3);
			bonusMap.put(sortedPerformances.get(1), 2);
			bonusMap.put(sortedPerformances.get(2), 1);
			return bonusMap;
		}
		
		// deli se prvo mesto
		if(sortedPerformances.get(0).getPoints() == sortedPerformances.get(1).getPoints() 
				&& sortedPerformances.get(1).getPoints() == sortedPerformances.get(2).getPoints()) {
			bonusMap.put(sortedPerformances.get(0), 3);
			bonusMap.put(sortedPerformances.get(1), 3);
			bonusMap.put(sortedPerformances.get(2), 3);
			return bonusMap;
		}
		
		if(sortedPerformances.get(0).getPoints() == sortedPerformances.get(1).getPoints() 
				&& sortedPerformances.get(1).getPoints() > sortedPerformances.get(2).getPoints()) {
			bonusMap.put(sortedPerformances.get(0), 3);
			bonusMap.put(sortedPerformances.get(1), 3);
			bonusMap.put(sortedPerformances.get(2), 2);
			return bonusMap;
		}
		
		// deli se drugo mesto
		if(sortedPerformances.get(0).getPoints() > sortedPerformances.get(1).getPoints() 
				&& sortedPerformances.get(1).getPoints() == sortedPerformances.get(2).getPoints()) {
			bonusMap.put(sortedPerformances.get(0), 3);
			bonusMap.put(sortedPerformances.get(1), 2);
			bonusMap.put(sortedPerformances.get(2), 2);
			return bonusMap;
		}
		
		// deli se trece mesto
		if(sortedPerformances.get(0).getPoints() > sortedPerformances.get(1).getPoints() 
				&& sortedPerformances.get(1).getPoints() > sortedPerformances.get(2).getPoints()
				&& sortedPerformances.get(2).getPoints() > sortedPerformances.get(3).getPoints()) {
			bonusMap.put(sortedPerformances.get(0), 3);
			bonusMap.put(sortedPerformances.get(1), 2);
			bonusMap.put(sortedPerformances.get(2), 1);
			bonusMap.put(sortedPerformances.get(3), 1);
			return bonusMap;
		}
		return bonusMap;
	}

}
