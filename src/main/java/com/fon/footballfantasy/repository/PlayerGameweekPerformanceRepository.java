package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.domain.PlayerGameweekPerformance;

public interface PlayerGameweekPerformanceRepository extends CrudRepository<PlayerGameweekPerformance, Long> {
	
	Long countByGameweekId(Long gameweekId);
	
	PlayerGameweekPerformance findByPlayerAndGameweekId(Player player, Long gameweekId);

	List<PlayerGameweekPerformance> findByPlayerId(Long playerId);
	
	@Query(value = "SELECT * FROM player_gameweek_performances WHERE gameweek_id = :gameweekId", nativeQuery = true)
	List<PlayerGameweekPerformance> findByGameweekId(@Param("gameweekId") Long gameweekId);

}
