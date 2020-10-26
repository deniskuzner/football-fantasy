package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.team.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

	Team findByUserId(Long userId);
	
	void deleteByUserId(Long userId);
	
}
