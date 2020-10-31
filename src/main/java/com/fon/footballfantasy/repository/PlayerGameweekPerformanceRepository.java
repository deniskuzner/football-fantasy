package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

public interface PlayerGameweekPerformanceRepository extends CrudRepository<PlayerGameweekPerformance, Long> {
	
	Long countByGameweekId(Long gameweekId);
	
	PlayerGameweekPerformance findByPlayerAndGameweekId(Player player, Long gameweekId);

	List<PlayerGameweekPerformance> findByPlayerId(Long playerId);

}
