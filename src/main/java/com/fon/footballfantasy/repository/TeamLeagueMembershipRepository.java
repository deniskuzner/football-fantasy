package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.league.TeamLeagueMembership;

public interface TeamLeagueMembershipRepository extends CrudRepository<TeamLeagueMembership, Long> {
	
	List<TeamLeagueMembership> findByTeamId(Long teamId);
	
	List<TeamLeagueMembership> findByLeagueId(Long leagueId);
	
	@Modifying
	@Query(value = "delete from team_league_memberships where team_id = :teamId and league_id = :leagueId", nativeQuery = true)
	long deleteByTeamAndLeague(@Param("teamId") Long teamId, @Param("leagueId") Long leagueId);

}
