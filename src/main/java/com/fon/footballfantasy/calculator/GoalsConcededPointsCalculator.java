package com.fon.footballfantasy.calculator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.domain.Substitution;
import com.fon.footballfantasy.service.dto.MinutesPlayedDetails;

@Component
public class GoalsConcededPointsCalculator {

	// Minus 1 point for every 2 goals conceded by a goalkeeper or defender
	public int calculate(PlayerGameweekPerformance pgp, Match match, MinutesPlayedDetails mpDetails, List<Substitution> playerSubstitutions) {
		Player player = pgp.getPlayer();
		int points = 0;
		
		if(player.getPosition().equals("MF") || player.getPosition().equals("FW")) {
			return points;
		}
		
		boolean isHostPlayer = player.getClub().getId() == match.getHost().getId() ? true : false;
		String[] results = match.getResult().split("-");
		
		//1. Igrao celu utakmicu
		if(mpDetails.getMinuteOut() - mpDetails.getMinuteIn() == 90) {
			// Host
			if(isHostPlayer) {
				points = -(Integer.parseInt(results[0])/2);
			} else {
			// Guest
				points = -(Integer.parseInt(results[1])/2);
			}
			return points;
		}
		
		//2. Igrao od pocetka pa izasao u nekom trenutku
		if(!mpDetails.getInSubstitution().isPresent() && mpDetails.getOutSubstitution().isPresent()) {
			Substitution outSubstitution = mpDetails.getOutSubstitution().get();
			String[] substitutionMomentResults = outSubstitution.getResult().split("-");
			if(isHostPlayer) {
				points = -(Integer.parseInt(substitutionMomentResults[0])/2);
			} else {
				points = -(Integer.parseInt(substitutionMomentResults[1])/2);
			}
			return points;
		}
		
		//3. Usao sa klupe i igrao do kraja
		if(mpDetails.getInSubstitution().isPresent() && !mpDetails.getOutSubstitution().isPresent()) {
			Substitution inSubstitution = mpDetails.getInSubstitution().get();
			String[] substitutionMomentResults = inSubstitution.getResult().split("-");
			if(isHostPlayer) {
				int goalsCount = Integer.parseInt(results[0]) - Integer.parseInt(substitutionMomentResults[0]);
				points = -(goalsCount/2);
			} else {
				int goalsCount = Integer.parseInt(results[1]) - Integer.parseInt(substitutionMomentResults[1]);
				points = -(goalsCount/2);
			}
			return points;
		}

		//4. Usao sa klupe pa izasao pre kraja
		if(mpDetails.getInSubstitution().isPresent() && mpDetails.getOutSubstitution().isPresent()) {
			Substitution inSubstitution = mpDetails.getInSubstitution().get();
			Substitution outSubstitution = mpDetails.getOutSubstitution().get();
			String[] inSubstitutionMomentResults = inSubstitution.getResult().split("-");
			String[] outSubstitutionMomentResults = outSubstitution.getResult().split("-");
			if(isHostPlayer) {
				int goalsCount = Integer.parseInt(outSubstitutionMomentResults[0]) - Integer.parseInt(inSubstitutionMomentResults[0]);
				points = -(goalsCount/2);
			} else {
				int goalsCount = Integer.parseInt(outSubstitutionMomentResults[1]) - Integer.parseInt(inSubstitutionMomentResults[1]);
				points = -(goalsCount/2);
			}
			return points;
		}
		
		return points;
	}

}
