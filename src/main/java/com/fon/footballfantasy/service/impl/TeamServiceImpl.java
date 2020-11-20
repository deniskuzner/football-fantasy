package com.fon.footballfantasy.service.impl;

import static com.fon.footballfantasy.exception.TeamException.TeamExceptionCode.TEAM_NOT_VALID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;
import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamGameweekPerformance;
import com.fon.footballfantasy.domain.team.TeamPlayer;
import com.fon.footballfantasy.exception.TeamException;
import com.fon.footballfantasy.repository.PlayerGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.TeamGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.TeamPlayerRepository;
import com.fon.footballfantasy.repository.TeamRepository;
import com.fon.footballfantasy.service.TeamService;

@Service
@Transactional
@Validated
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TeamPlayerRepository teamPlayerRepository;

	@Autowired
	TeamGameweekPerformanceRepository tgpRepository;

	@Autowired
	PlayerGameweekPerformanceRepository pgpRepository;

	@Override
	public List<Team> calculateGameweekPoints(Long gameweekId) {
		// check if player points are calculated for that gameweek
		if (pgpRepository.countByGameweekId(gameweekId) == 0) {
			//TODO: BACITI CUSTOM EXCEPTION
			return new ArrayList<>();
		}
		
		List<Team> teams = (List<Team>) teamRepository.findAll();
		for (Team team : teams) {
			calculateTeamGameweekPoints(team, gameweekId);
		}
		
		//TODO: UPDATE GAMEWEEKSTATUS NA CONFIRMED
		
		return teams;
	}

	@Override
	public Team calculateTeamGameweekPoints(Team team, Long gameweekId) {
		// check if team points are calculated for that gameweek
		TeamGameweekPerformance tgp = tgpRepository.findByTeamAndGameweekId(team, gameweekId);
		if (tgp != null) {
			tgpRepository.delete(tgp);
		}

		List<TeamPlayer> players = team.getTeamPlayers();
		for (TeamPlayer tp : players) {
			// TODO: MOZDA ZAMENITI SA
			// pgpRepository.findByPlayerAndGameweekId(tp.getPlayer(), gameweekId);
			PlayerGameweekPerformance pgp = tp.getPlayer().getPlayerGameweekPerformances().stream()
					.filter(p -> p.getGameweek().getId() == gameweekId).findFirst().get();
			int points = pgp.getPoints();
			tp.setPoints(points);
		}

		setCaptainPoints(team);

		int totalGameweekPoints = players.stream().filter(p -> !p.isOnBench()).mapToInt(p -> p.getPoints()).sum();
		int totalTeamPoints = tgpRepository.getTeamPoints(team.getId());
		team.setTotalPoints(totalTeamPoints + totalGameweekPoints);

		team.getTeamGameweekPerformances().add(TeamGameweekPerformance.builder().points(totalGameweekPoints).team(team)
				.gameweek(Gameweek.builder().id(gameweekId).build()).build());

		return save(team);
	}

	@Override
	public Team save(Team team) {
		validateTeam(team);
		if(team.getId() != null) {
			teamPlayerRepository.deleteByTeamId(team.getId());
			team.setModifiedOn(null);
		}
		for (TeamPlayer tp : team.getTeamPlayers()) {
			tp.setTeam(team);
		}
		return teamRepository.save(team);
	}

	@Override
	public List<Team> findAll() {
		return (List<Team>) teamRepository.findAll();
	}

	@Override
	public Team findById(Long id) {
		return teamRepository.findById(id).get();
	}

	@Override
	public Team findByUserId(Long userId) {
		return teamRepository.findByUserId(userId);
	}

	@Override
	public void deleteById(Long id) {
		teamRepository.deleteById(id);
	}

	@Override
	public void deleteByUserId(Long userId) {
		teamRepository.deleteByUserId(userId);
	}

	// Private helper methods

	private boolean validateTeam(Team team) {
		List<TeamPlayer> players = team.getTeamPlayers();

		if (!validateTeamSize(players))
			throw new TeamException(TEAM_NOT_VALID, "Invalid team size!");

		if (!validateTeamDistinctPlayers(players))
			throw new TeamException(TEAM_NOT_VALID, "Team contains duplicate players!");

		if (!validateTeamPositions(players))
			throw new TeamException(TEAM_NOT_VALID, "Invalid player positions!");
		
		if (!validateBenchSize(players))
			throw new TeamException(TEAM_NOT_VALID, "Invalid bench size!");

		if (!validateTeamClubsLimit(players))
			throw new TeamException(TEAM_NOT_VALID, "You can select up to 3 players from a single club!");
		
		//TODO: AKO SE PRVI PUT PRAVI TIM, PROVERITI DA LI KOSTA ISPOD 100

		return true;
	}

	private boolean validateTeamSize(List<TeamPlayer> players) {
		if (players.size() != 15) {
			return false;
		}
		return true;
	}

	private boolean validateTeamDistinctPlayers(List<TeamPlayer> players) {
		List<TeamPlayer> distinctPlayers = players.stream().filter(distinctByKey(p -> p.getPlayer().getId()))
				.collect(Collectors.toList());
		if (distinctPlayers.size() != 15) {
			return false;
		}
		return true;
	}

	private boolean validateTeamPositions(List<TeamPlayer> players) {
		List<TeamPlayer> gk = players.stream().filter(p -> p.getPlayer().getPosition().equals("GK"))
				.collect(Collectors.toList());
		List<TeamPlayer> df = players.stream().filter(p -> p.getPlayer().getPosition().equals("DF"))
				.collect(Collectors.toList());
		List<TeamPlayer> mf = players.stream().filter(p -> p.getPlayer().getPosition().equals("MF"))
				.collect(Collectors.toList());
		List<TeamPlayer> fw = players.stream().filter(p -> p.getPlayer().getPosition().equals("FW"))
				.collect(Collectors.toList());

		if (gk.size() != 2 || df.size() != 5 || mf.size() != 5 || fw.size() != 3) {
			return false;
		}
		return true;
	}

	private boolean validateBenchSize(List<TeamPlayer> players) {
		long gkOnBench = players.stream().filter(p -> p.isOnBench() && p.getPlayer().getPosition().equals("GK")).count();
		long dfOnBench = players.stream().filter(p -> p.isOnBench() && p.getPlayer().getPosition().equals("DF")).count();
		long mfOnBench = players.stream().filter(p -> p.isOnBench() && p.getPlayer().getPosition().equals("MF")).count();
		long fwOnBench = players.stream().filter(p -> p.isOnBench() && p.getPlayer().getPosition().equals("FW")).count();

		if (gkOnBench != 1 || dfOnBench != 1 || mfOnBench != 1 || fwOnBench != 1) {
			return false;
		}
		return true;
	}

	private boolean validateTeamClubsLimit(List<TeamPlayer> players) {
		List<Club> distinctClubs = players.stream().filter(distinctByKey(p -> p.getPlayer().getClub().getId()))
				.map(p -> p.getPlayer().getClub()).collect(Collectors.toList());

		for (Club club : distinctClubs) {
			long clubCount = players.stream().filter(p -> p.getPlayer().getClub().getId() == club.getId()).count();
			if (clubCount > 3) {
				return false;
			}
		}
		return true;
	}

	private void setCaptainPoints(Team team) {
		TeamPlayer captain = team.getTeamPlayers().stream().filter(p -> p.getPlayer().getId() == team.getCaptainId())
				.findFirst().get();
		TeamPlayer viceCaptain = team.getTeamPlayers().stream()
				.filter(p -> p.getPlayer().getId() == team.getViceCaptainId()).findFirst().get();
		int captainPoints = captain.getPoints();
		int viceCaptainPoints = viceCaptain.getPoints();

		if (captainPoints > 0) {
			captain.setPoints(captainPoints * 2);
		} else if (viceCaptainPoints > 0) {
			viceCaptain.setPoints(viceCaptainPoints * 2);
		}
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
