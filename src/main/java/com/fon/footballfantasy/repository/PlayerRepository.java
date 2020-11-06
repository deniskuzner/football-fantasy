package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
	
	Player findByUrl(String url);
	
	List<Player> findByOrderByClubNameAsc();
	
	@Modifying
	@Query(value = "UPDATE players SET total_points = :totalPoints, price = :price WHERE id = :id", nativeQuery = true)
	void updateTotalPointsAndPrice(@Param("id") Long id, @Param("totalPoints") int totalPoints, @Param("price") double price);
	
	List<Player> findByOrderByTotalPointsDesc();

	List<Player> findByClubId(Long clubId);

}
