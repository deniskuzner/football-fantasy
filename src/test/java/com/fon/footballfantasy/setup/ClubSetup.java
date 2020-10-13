package com.fon.footballfantasy.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.service.ClubService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClubSetup {
	
	@Autowired
	ClubService clubService;
	
	public List<Club> getSetup() {
		
		log.info("Saving club with players");
		
		List<Club> clubs = new ArrayList<>();
		
		Club club1 = Club.builder().name("Partizan").url("/dde3e804/Partizan")
				.image("https://d2p3bygnnzw9w3.cloudfront.net/req/202003242/tlogo/fb/dde3e804.png").build();
		
		Player p1 = Player.builder().name("Saša Zdjelar").url("/bbddc31f/Sasa-Zdjelar")
				.nationality("SRB").birthDate("March 20, 1995").age("25").position("MF")
				.height("183cm").weight("75kg").image("https://d6rt22vwfyr3i.cloudfront.net/req/201909271/images/headshots/bbddc31f_2018.jpg")
				.club(club1).build();
		Player p2 = Player.builder().name("Seydouba Soumah").url("/57ae16f4/Seydouba-Soumah")
				.nationality("GUI").birthDate("June 11, 1991").age("28").position("AM")
				.height("161cm").weight("").image("https://d6rt22vwfyr3i.cloudfront.net/req/201909271/images/headshots/57ae16f4_2018.jpg")
				.club(club1).build();
		
		club1.setPlayers(Arrays.asList(p1,p2));
		

		Club club2 = Club.builder().name("Vozdovac").url("/5379325a/Vozdovac-Stats")
				.image("https://d2p3bygnnzw9w3.cloudfront.net/req/202009101/tlogo/fb/5379325a.png").build();
		
		Player p3 = Player.builder().name("Marko Gajic").url("/c599efd8/Marko-Gajic")
				.nationality("SRB").birthDate("March 20, 1995").age("25").position("DF")
				.height("183cm").weight("75kg").image("https://d6rt22vwfyr3i.cloudfront.net/req/201909271/images/headshots/bbddc31f_2018.jpg")
				.club(club2).build();
		Player p4 = Player.builder().name("Marko Putinčanin").url("/ada99c06/Marko-Putincanin")
				.nationality("SRB").birthDate("June 11, 1991").age("28").position("FW")
				.height("185cm").weight("78kg").image("https://fbref.com/req/202005121/images/headshots/ada99c06_2018.jpg")
				.club(club2).build();
		
		club2.setPlayers(Arrays.asList(p3,p4));
		
		clubs.add(club1);
		clubs.add(club2);
		
		clubs.forEach(c -> {
			clubService.save(c);
		});
		
		return clubs;
	}

}
