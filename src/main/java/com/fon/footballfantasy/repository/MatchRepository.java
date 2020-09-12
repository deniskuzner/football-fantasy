package com.fon.footballfantasy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.domain.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {
	
	Match findByUrl(String url);
	
	Match findByHostAndGuestAndGameweek(Club host, Club guest, Gameweek gameweek);
	
	List<Match> findByDateTimeBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
