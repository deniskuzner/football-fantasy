package com.fon.footballfantasy.calculator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.domain.Substitution;
import com.fon.footballfantasy.service.dto.MinutesPlayedDetails;

@Component
public class CleanSheetPointsCalculator {

	public int calculate(PlayerGameweekPerformance pgp, Match match, MinutesPlayedDetails mpDetails, List<Substitution> playerSubstitutions) {
		Player player = pgp.getPlayer();
		int points = 0;
		
		boolean isHostPlayer = player.getClub().getId() == match.getHost().getId() ? true : false;
		String[] results = match.getResult().split("-");
		if (!mpDetails.isCleanSheetCandidate()) {
			return points;
		}

		//1. Final result clean sheet
		if ((isHostPlayer && results[0].equals("0")) || (!isHostPlayer && results[1].equals("0"))) {
			points = getCleanSheetPoints(player);
			return points;
		}

		//2. Igrao od pocetka pa izasao u nekom trenutku
		if (!mpDetails.getInSubstitution().isPresent() && mpDetails.getOutSubstitution().isPresent()) {
			Substitution outSubstitution = mpDetails.getOutSubstitution().get();
			String[] substitutionMomentResults = outSubstitution.getResult().split("-");
			if ((isHostPlayer && substitutionMomentResults[0].equals("0"))
					|| (!isHostPlayer && substitutionMomentResults[1].equals("0"))) {
				points = getCleanSheetPoints(player);
				return points;
			}
		}

		//3. Usao sa klupe i igrao do kraja
		if (mpDetails.getInSubstitution().isPresent() && !mpDetails.getOutSubstitution().isPresent()) {
			Substitution inSubstitution = mpDetails.getInSubstitution().get();
			String[] substitutionMomentResults = inSubstitution.getResult().split("-");
			if ((isHostPlayer && substitutionMomentResults[0].equals(results[0]))
					|| (!isHostPlayer && substitutionMomentResults[1].equals(results[1]))) {
				points = getCleanSheetPoints(player);
				return points;
			}
		}
		
		//4. Usao sa klupe pa izasao pre kraja
		if(mpDetails.getInSubstitution().isPresent() && mpDetails.getOutSubstitution().isPresent()) {
			Substitution inSubstitution = mpDetails.getInSubstitution().get();
			Substitution outSubstitution = mpDetails.getOutSubstitution().get();
			String[] inSubstitutionMomentResults = inSubstitution.getResult().split("-");
			String[] outSubstitutionMomentResults = outSubstitution.getResult().split("-");
			if((isHostPlayer && outSubstitutionMomentResults[0].equals(inSubstitutionMomentResults[0])) 
					|| (!isHostPlayer && outSubstitutionMomentResults[1].equals(inSubstitutionMomentResults[1]))) {
				points = getCleanSheetPoints(player);
				return points;
			}
		}
		return points;
	}

	private int getCleanSheetPoints(Player player) {
		int points = 0;
		if (player.getPosition().equals("GK")) {
			points = 4;
		}
		if (player.getPosition().equals("MF")) {
			points = 1;
		}
		return points;
	}

}
