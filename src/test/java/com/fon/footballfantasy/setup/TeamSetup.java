package com.fon.footballfantasy.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.User;
import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamGameweekPerformance;
import com.fon.footballfantasy.domain.team.TeamPlayer;
import com.fon.footballfantasy.repository.ClubRepository;
import com.fon.footballfantasy.repository.GameweekRepository;
import com.fon.footballfantasy.repository.TeamRepository;
import com.fon.footballfantasy.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TeamSetup {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	GameweekRepository gameweekRepository;

	@Autowired
	ClubSetup clubSetup;

	public Team getSetup() {
		List<Club> clubs = clubSetup.getSetup();
		Club club = clubs.get(0);
		List<Player> players = club.getPlayers();
		Gameweek gameweek = Gameweek.builder().orderNumber(1).build();
		gameweekRepository.save(gameweek);

		User user = User.builder().username("pera").password("pera").firstName("Pera").lastName("Peric")
				.email("pera@gmail.com").gender("MALE").birthDate("20-20-2020").phoneNumber("06123456789")
				.country("Serbia").favouriteClub(club).build();
		userRepository.save(user);

		log.info("Saving user team with players");

		Team team = Team.builder().totalPoints(15).freeTransfers(2).name("teleoptik").userId(user.getId())
				.captainId(players.get(0).getId()).viceCaptainId(players.get(1).getId()).build();

		List<TeamPlayer> teamPlayers = new ArrayList<>();
		teamPlayers.add(TeamPlayer.builder().points(10).onBench(false).player(players.get(0)).team(team).build());
		teamPlayers.add(TeamPlayer.builder().points(5).onBench(true).player(players.get(1)).team(team).build());
		team.setTeamPlayers(teamPlayers);

		List<TeamGameweekPerformance> teamGameweekPerformances = new ArrayList<>();
		teamGameweekPerformances
				.add(TeamGameweekPerformance.builder().points(15).team(team).gameweek(gameweek).build());
		team.setTeamGameweekPerformances(teamGameweekPerformances);

		teamRepository.save(team);
		return team;
	}

	public Team getFullValidSetup() {

		List<Club> fullSetup = clubSetup.getFullSetup();
		Club favourite = fullSetup.get(0);
		Gameweek gameweek = Gameweek.builder().orderNumber(1).build();
		gameweekRepository.save(gameweek);

		User user = User.builder().username("pera").password("pera").firstName("Pera").lastName("Peric")
				.email("pera@gmail.com").gender("MALE").birthDate("20-20-2020").phoneNumber("06123456789")
				.country("Serbia").favouriteClub(favourite).build();
		userRepository.save(user);

		Team team = Team.builder().totalPoints(15).freeTransfers(2).name("teleoptik").userId(user.getId())
				.captainId(favourite.getPlayers().get(0).getId()).viceCaptainId(favourite.getPlayers().get(1).getId()).build();

		List<TeamPlayer> teamPlayers = new ArrayList<>();
		for (Club club: fullSetup) {
			for (Player player : club.getPlayers()) {
				teamPlayers.add(TeamPlayer.builder().points(10).onBench(false).player(player).team(team).build());
			}
		}
		//set on bench
		teamPlayers.stream().filter(tp -> tp.getPlayer().getPosition().equals("GK")).findFirst().get().setOnBench(true);
		teamPlayers.stream().filter(tp -> tp.getPlayer().getPosition().equals("DF")).findFirst().get().setOnBench(true);
		teamPlayers.stream().filter(tp -> tp.getPlayer().getPosition().equals("MF")).findFirst().get().setOnBench(true);
		teamPlayers.stream().filter(tp -> tp.getPlayer().getPosition().equals("FW")).findFirst().get().setOnBench(true);
		
		team.setTeamPlayers(teamPlayers);
		
		return team;
	}

	public void deleteAll() {
		teamRepository.deleteAll();
		userRepository.deleteAll();
		clubRepository.deleteAll();
		gameweekRepository.deleteAll();
	}

}
