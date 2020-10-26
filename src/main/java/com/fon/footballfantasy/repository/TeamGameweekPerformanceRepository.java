package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamGameweekPerformance;

public interface TeamGameweekPerformanceRepository extends CrudRepository<TeamGameweekPerformance, Long> {
	
	TeamGameweekPerformance findByTeamAndGameweekId(Team team, Long gameweekId);

}
