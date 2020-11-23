package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.domain.team.TeamGameweekPerformance;

public interface TeamGameweekPerformanceRepository extends CrudRepository<TeamGameweekPerformance, Long> {
	
	List<TeamGameweekPerformance> findByTeam(Team team);
	
	TeamGameweekPerformance findByTeamAndGameweekId(Team team, Long gameweekId);
	
	@Query(value = "select sum(points) from team_gameweek_performances where team_id = :teamId", nativeQuery = true)
	int getTeamPoints(@Param("teamId") Long teamId);
	
	@Query(value = "select avg(points), "
						+ "max(points) "
						+ "from team_gameweek_performances where gameweek_id = :gameweekId", nativeQuery = true)
	List<Object[]> getGameweekStats(@Param("gameweekId") Long gameweekId);

	@Query(value = "select (rank() over (order by points desc)) as rank "
			 	 + "from team_gameweek_performances "
			 	 + "where team_id = :teamId and gameweek_id = :gameweekId", nativeQuery = true)
	List<Object[]> getRank(@Param("teamId") Long teamId, @Param("gameweekId") Long gameweekId);
	
}
