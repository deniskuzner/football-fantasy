package com.fon.footballfantasy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {
	
	Match findByUrl(String url);
	
	Match findByHostAndGuestAndGameweek(Club host, Club guest, Gameweek gameweek);
	
	List<Match> findBySentAndDateTimeBetween(boolean sent, LocalDateTime fromDate, LocalDateTime toDate);
	
	List<Match> findBySentAndGameweekId(boolean sent, Long gameweekId);

	@Modifying()
	@Query(value = "update matches set sent = true where id = :id", nativeQuery = true)
	int updateSent(@Param("id") Long id);
	
	List<Match> findByGameweekOrderNumber(int gameweekOrderNumber);
	
}
