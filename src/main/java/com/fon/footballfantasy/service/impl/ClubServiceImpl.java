package com.fon.footballfantasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.parser.SeasonClubPageHtmlParser;
import com.fon.footballfantasy.repository.ClubRepository;
import com.fon.footballfantasy.service.ClubService;

@Service
@Transactional
@Validated
public class ClubServiceImpl implements ClubService {
	
	@Autowired
	SeasonClubPageHtmlParser clubParser;
	
	@Autowired
	ClubRepository clubRepository;

	@Override
	public List<Club> saveSeasonClubs() {
		List<Club> clubs = clubParser.getSeasonClubs();
		for (Club club : clubs) {
			save(club);
		}
		return clubs;
	}

	@Override
	public Club save(Club club) {
		return clubRepository.save(club);
	}

	@Override
	public Club getById(Long id) {
		return clubRepository.findById(id).get();
	}

	@Override
	public List<Club> getAll() {
		return (List<Club>) clubRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		clubRepository.deleteById(id);
	}

}
