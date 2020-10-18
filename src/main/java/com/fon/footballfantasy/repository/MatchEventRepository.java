package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.event.MatchEvent;

public interface MatchEventRepository extends CrudRepository<MatchEvent, Long> {
	
	List<MatchEvent> findByMatchId(Long matchId);

}
