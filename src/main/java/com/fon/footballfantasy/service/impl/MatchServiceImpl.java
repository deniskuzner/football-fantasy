package com.fon.footballfantasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.repository.MatchRepository;
import com.fon.footballfantasy.service.ClubService;
import com.fon.footballfantasy.service.MatchService;

@Service
@Transactional
@Validated
public class MatchServiceImpl implements MatchService {
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	ClubService clubService;

	@Override
	public Match save(Match match) {
		// Postavljanje klubova
		if(match.getHost().getId() == null || match.getGuest().getId() == null)
			setClubs(match);
		
		// Provera da li vec postoji mec u bazi
		Match m = findMatch(match);
		if(m != null) {
			match.setId(m.getId());
			match.setCreatedOn(m.getCreatedOn());
		}
		
		return matchRepository.save(match);
	}

	@Override
	public Match findById(Long id) {
		return matchRepository.findById(id).get();
	}
	
	@Override
	public Match findByUrl(String url) {
		return matchRepository.findByUrl(url);
	}

	@Override
	public List<Match> findAll() {
		return (List<Match>) matchRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		matchRepository.deleteById(id);
	}
	
	private void setClubs(Match match) {
		match.setHost(clubService.findByUrl(match.getHost().getUrl()));
		match.setGuest(clubService.findByUrl(match.getGuest().getUrl()));
		if(match.getHost() == null || match.getGuest() == null) {
			//TODO Baciti custom exception sa porukom "Match (id: %id): clubs don't exist!"
		}
	}
	
	private Match findMatch(Match match) {
		return matchRepository.findByHostAndGuestAndGameweek(match.getHost(), match.getGuest(), match.getGameweek());
	}

}
