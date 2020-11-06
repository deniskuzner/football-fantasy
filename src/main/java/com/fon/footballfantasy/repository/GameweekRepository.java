package com.fon.footballfantasy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.Gameweek;

public interface GameweekRepository extends CrudRepository<Gameweek, Long> {
	
	Gameweek findByOrderNumber(int orderNumber);
	
	@Query(value = "SELECT gameweek_id FROM matches ORDER BY ABS(DATEDIFF(NOW(), date_time)) LIMIT 1", nativeQuery = true)
	Long findCurrentGameweekId();
	
	@Query(value = "SELECT order_number FROM gameweeks WHERE id = :id", nativeQuery = true)
	int findOrderNumberById(@Param("id") Long id);
	
}
