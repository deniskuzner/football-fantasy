package com.fon.footballfantasy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.calculator.PlayerPriceCalculator;
import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.repository.PlayerRepository;
import com.fon.footballfantasy.service.PlayerService;

@Service
@Transactional
@Validated
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	PlayerPriceCalculator priceCalculator;

	@Override
	public Player save(Player player) {
		Player p = playerRepository.findByUrl(player.getUrl());
		if(p != null) {
			player.setId(p.getId());
			player.setCreatedOn(p.getCreatedOn());
			player.setPrice(p.getPrice());
			player.setTotalPoints(p.getTotalPoints());
		} else {
			player.setPrice(priceCalculator.getBasePrice(player));
		}
		return playerRepository.save(player);
	}

	@Override
	public Player findById(Long id) {
		return playerRepository.findById(id).get();
	}
	
	@Override
	public Player findByUrl(String url) {
		return playerRepository.findByUrl(url);
	}

	@Override
	public List<Player> findAll() {
		return (List<Player>) playerRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		playerRepository.deleteById(id);
	}

	@Override
	public List<Player> findAllOrderByPointsDesc() {
		return playerRepository.findByOrderByTotalPointsDesc();
	}

	@Override
	public List<Player> findByClubId(Long clubId) {
		return playerRepository.findByClubId(clubId);
	}

}
