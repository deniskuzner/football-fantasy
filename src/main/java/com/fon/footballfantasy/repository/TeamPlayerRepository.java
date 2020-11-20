package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.team.TeamPlayer;

public interface TeamPlayerRepository extends CrudRepository<TeamPlayer, Long> {
	
	long deleteByTeamId(Long teamId);

}
