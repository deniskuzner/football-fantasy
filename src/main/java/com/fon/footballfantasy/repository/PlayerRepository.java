package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
	
	Player findByUrl(String url);
	
	List<Player> findByOrderByClubNameAsc();

}
