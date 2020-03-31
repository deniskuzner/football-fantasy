package com.fon.footballfantasy.setup;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.repository.ClubRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClubSetup {
	
	@Autowired
	ClubRepository clubRepository;
	
	public Club getSetup() {
		
		log.info("Saving club with players");
		
		Club club = Club.builder().name("Partizan").url("/dde3e804/Partizan")
				.image("https://d2p3bygnnzw9w3.cloudfront.net/req/202003242/tlogo/fb/dde3e804.png").build();
		
		Player p1 = Player.builder().name("Saša Zdjelar").url("/bbddc31f/Sasa-Zdjelar")
				.nationality("SRB").birthDate("March 20, 1995").age("25").position("MF")
				.height("183cm").weight("75kg").image("https://d6rt22vwfyr3i.cloudfront.net/req/201909271/images/headshots/bbddc31f_2018.jpg")
				.club(club).build();
		Player p2 = Player.builder().name("Seydouba Soumah").url("/57ae16f4/Seydouba-Soumah")
				.nationality("GUI").birthDate("June 11, 1991").age("28").position("AM")
				.height("161cm").weight("").image("https://d6rt22vwfyr3i.cloudfront.net/req/201909271/images/headshots/57ae16f4_2018.jpg")
				.club(club).build();
		
		club.setPlayers(Arrays.asList(p1,p2));
		
		clubRepository.save(club);
		
		return club;
	}

}
