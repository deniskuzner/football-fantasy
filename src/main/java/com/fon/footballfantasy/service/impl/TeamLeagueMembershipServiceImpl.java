package com.fon.footballfantasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.league.TeamLeagueMembership;
import com.fon.footballfantasy.repository.TeamLeagueMembershipRepository;
import com.fon.footballfantasy.service.TeamLeagueMembershipService;

@Service
@Transactional
@Validated
public class TeamLeagueMembershipServiceImpl implements TeamLeagueMembershipService {

	@Autowired
	TeamLeagueMembershipRepository tlmRepository;

	@Override
	public TeamLeagueMembership save(TeamLeagueMembership tlm) {
		return tlmRepository.save(tlm);
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
	
}
