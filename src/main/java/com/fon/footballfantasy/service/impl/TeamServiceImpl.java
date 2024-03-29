package com.fon.footballfantasy.service.impl;

import static com.fon.footballfantasy.exception.TeamException.TeamExceptionCode.TEAM_NOT_VALID;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.league.TeamLeagueMembership;
import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamPlayer;
import com.fon.footballfantasy.exception.TeamException;
import com.fon.footballfantasy.repository.PlayerGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.TeamGameweekPerformanceRepository;
import com.fon.footballfantasy.repository.TeamPlayerRepository;
import com.fon.footballfantasy.repository.TeamRepository;
import com.fon.footballfantasy.service.TeamLeagueMembershipService;
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
	
	@Autowired
	TeamLeagueMembershipService tlmService;

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
	public Team update(Team team) {
		validateTeam(team);
		if(team.getId() != null) {
			team.setModifiedOn(null);
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
	public List<Team> findByLeagueId(Long leagueId) {
		List<Team> teams = new ArrayList<>();
		List<TeamLeagueMembership> memberships = tlmService.findByLeagueId(leagueId);
		for (TeamLeagueMembership tlm : memberships) {
			teams.add(findById(tlm.getTeamId()));
		}
		return teams.stream().sorted(Comparator.comparingInt(Team::getTotalPoints).reversed()).collect(Collectors.toList());
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

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
