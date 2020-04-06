package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.setup.ClubSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ClubRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	ClubRepository clubRepository;
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	ClubSetup clubSetup;
	
	@Test
	void testCrud() {
		
		log.info("Getting setup");
		
		Club club = clubSetup.getSetup();
		Player p1 = club.getPlayers().get(0);
		Player p2 = club.getPlayers().get(1);
		
		log.info("Getting club with players");
		
		Club c = clubRepository.findById(club.getId()).get();
		assertNotNull(club);
		assertEquals(club.getId(), c.getId());
		assertEquals(club.getName(), c.getName());
		assertEquals(club.getUrl(), c.getUrl());
		assertEquals(club.getPlayers().size(), c.getPlayers().size());
		
		assertEquals(p1.getName(), c.getPlayers().get(0).getName());
		assertEquals(p2.getName(), c.getPlayers().get(1).getName());
		
		log.info("Updating a player");
		
		p1.setAge("26");
		p2.setAge("29");
		playerRepository.save(p1);
		playerRepository.save(p2);
		
		Club updatedClub = clubRepository.findById(club.getId()).get();
		assertEquals("26", updatedClub.getPlayers().get(0).getAge());
		assertEquals("29", updatedClub.getPlayers().get(1).getAge());
		
		log.info("Deleting clubs with players");
		
		clubRepository.deleteAll();
		assertEquals(0, ((List<Club>) clubRepository.findAll()).size());
		assertEquals(0, ((List<Player>) playerRepository.findAll()).size());
		
	}

}
