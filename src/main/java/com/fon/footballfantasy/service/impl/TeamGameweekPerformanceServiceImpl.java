package com.fon.footballfantasy.service.impl;

import static com.fon.footballfantasy.exception.TeamGameweekPerformanceException.TeamGameweekPerformanceExceptionCode.PLAYER_POINTS_NOT_CALCULATED;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamGameweekPerformance;
import com.fon.footballfantasy.domain.team.TeamPlayer;
import com.fon.footballfantasy.exception.TeamGameweekPerformanceException;
import com.fon.footballfantasy.repository.PlayerGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.TeamGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.TeamRepository;
import com.fon.footballfantasy.service.TeamGameweekPerformanceService;
import com.fon.footballfantasy.service.TeamService;
import com.fon.footballfantasy.service.dto.TeamGameweekStats;

@Service
@Transactional
@Validated
public class TeamGameweekPerformanceServiceImpl implements TeamGameweekPerformanceService {

	@Autowired
	TeamService teamService;

	@Autowired
	TeamGameweekPerformanceRepository tgpRepository;

	@Autowired
	PlayerGameweekPerformanceRepository pgpRepository;

	@Autowired
	TeamRepository teamRepository;

	@Override
	public List<Team> calculateGameweekPoints(Long gameweekId) {
		// check if player points are calculated for that gameweek
		if (pgpRepository.countByGameweekId(gameweekId) == 0) {
			throw new TeamGameweekPerformanceException(PLAYER_POINTS_NOT_CALCULATED, "Player points for gameweek: %s are not calculated!", gameweekId);
		}

		List<Team> teams = (List<Team>) teamRepository.findAll();
		for (Team team : teams) {
			calculateTeamGameweekPoints(team, gameweekId);
		}

		// TODO: UPDATE GAMEWEEKSTATUS NA CONFIRMED

		return teams;
	}
	
	public Team calculateTeamGameweekPoints(Team team, Long gameweekId) {
		TeamGameweekPerformance tgp = TeamGameweekPerformance.builder().team(team)
				.gameweek(Gameweek.builder().id(gameweekId).build()).build();
		
		// check if team points are calculated for that gameweek
		TeamGameweekPerformance teamPerformance = tgpRepository.findByTeamAndGameweekId(team, gameweekId);
		int currentGWPoints = 0;
		if (teamPerformance != null) {
			tgp.setId(teamPerformance.getId());
			tgp.setCreatedOn(teamPerformance.getCreatedOn());
			currentGWPoints = teamPerformance.getPoints();
		}

		List<TeamPlayer> players = team.getTeamPlayers();
		for (TeamPlayer tp : players) {
			Optional<PlayerGameweekPerformance> pgp = tp.getPlayer().getPlayerGameweekPerformances().stream()
					.filter(p -> p.getGameweek().getId().equals(gameweekId)).findFirst();
			if(pgp.isPresent()) {
				tp.setPoints(pgp.get().getPoints());
			} else {
				tp.setPoints(0);
			}
		}

		setCaptainPoints(team);
		int totalGameweekPoints = players.stream().filter(p -> !p.isOnBench()).mapToInt(p -> p.getPoints()).sum();
		int newPoints = totalGameweekPoints - currentGWPoints;
		team.setTotalPoints(team.getTotalPoints() + newPoints);
		
		tgp.setPoints(totalGameweekPoints);
		if(tgp.getId() == null) {
			tgpRepository.save(tgp);			
		}
		
		return team;
	}
	
	@Override
	public TeamGameweekStats getGameweekStats(Long teamId, Long gameweekId) {
		Object[] stats = tgpRepository.getGameweekStats(gameweekId).get(0);
		int averagePoints = ((BigDecimal) stats[0]).intValue();
		int highestPoints = (int) stats[1];
		int rank = ((BigInteger) tgpRepository.getRank(teamId, gameweekId).get(0)[0]).intValue();
		return TeamGameweekStats.builder().averagePoints(averagePoints).highestPoints(highestPoints).rank(rank).build();
	}

	// Private helper methods

	private void setCaptainPoints(Team team) {
		TeamPlayer captain = team.getTeamPlayers().stream()
				.filter(p -> p.getPlayer().getId().equals(team.getCaptainId())).findFirst().get();
		TeamPlayer viceCaptain = team.getTeamPlayers().stream()
				.filter(p -> p.getPlayer().getId().equals(team.getViceCaptainId())).findFirst().get();
		int captainPoints = captain.getPoints();
		int viceCaptainPoints = viceCaptain.getPoints();

		if (captainPoints > 0) {
			captain.setPoints(captainPoints * 2);
		} else if (viceCaptainPoints > 0) {
			viceCaptain.setPoints(viceCaptainPoints * 2);
		}
	}

}
