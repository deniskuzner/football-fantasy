package com.fon.footballfantasy.service.impl;

import static com.fon.footballfantasy.exception.LeagueException.LeagueExceptionCode.LEAGUE_MEMBER_DUPLICATE;
import static com.fon.footballfantasy.exception.LeagueException.LeagueExceptionCode.LEAGUE_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.league.League;
import com.fon.footballfantasy.domain.league.TeamLeagueMembership;
import com.fon.footballfantasy.exception.LeagueException;
import com.fon.footballfantasy.repository.LeagueRepository;
import com.fon.footballfantasy.repository.TeamLeagueMembershipRepository;
import com.fon.footballfantasy.service.TeamLeagueMembershipService;

@Service
@Transactional
@Validated
public class TeamLeagueMembershipServiceImpl implements TeamLeagueMembershipService {

	@Autowired
	TeamLeagueMembershipRepository tlmRepository;
	
	@Autowired
	LeagueRepository leagueRepository;

	@Override
	public League save(TeamLeagueMembership tlm) {
		Optional<League> league = leagueRepository.findById(tlm.getLeagueId());
		if(!league.isPresent()) {
			throw new LeagueException(LEAGUE_NOT_FOUND, "League ID: %s not found!", tlm.getLeagueId());
		}
		TeamLeagueMembership existingTlm = tlmRepository.findByTeamIdAndLeagueId(tlm.getTeamId(), tlm.getLeagueId());
		if(existingTlm != null) {
			throw new LeagueException(LEAGUE_MEMBER_DUPLICATE, "You are already member of league ID: %s!", tlm.getLeagueId());
		}
		tlmRepository.save(tlm);
		return league.get();
	}

	@Override
	public TeamLeagueMembership findById(Long id) {
		return tlmRepository.findById(id).get();
	}

	@Override
	public List<TeamLeagueMembership> findByTeamId(Long teamId) {
		return tlmRepository.findByTeamId(teamId);
	}

	@Override
	public List<TeamLeagueMembership> findByLeagueId(Long leagueId) {
		return tlmRepository.findByLeagueId(leagueId);
	}

	@Override
	public void deleteById(Long id) {
		tlmRepository.deleteById(id);
	}

	@Override
	public void deleteByTeamAndLeague(Long teamId, Long leagueId) {
		tlmRepository.deleteByTeamAndLeague(teamId, leagueId);
	}

	@Override
	public void deleteByLeagueId(Long id) {
		tlmRepository.deleteByLeagueId(id);
	}
	
}
