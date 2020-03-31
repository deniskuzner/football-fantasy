package com.fon.footballfantasy.service.impl;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.repository.PlayerRepository;
import com.fon.footballfantasy.service.PlayerService;

@Service
@Transactional
@Validated
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;

	@Override
	public Player save(Player player) {
		return playerRepository.save(player);
	}

	@Override
	public Player getById(@NotNull @Min(1) Long id) {
		return playerRepository.findById(id).get();
	}

	@Override
	public List<Player> getAll() {
		return (List<Player>) playerRepository.findAll();
	}

	@Override
	public void deleteById(@NotNull @Min(1) Long id) {
		playerRepository.deleteById(id);
	}

}
