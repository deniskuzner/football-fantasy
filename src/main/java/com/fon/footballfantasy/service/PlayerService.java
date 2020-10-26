package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Player;

public interface PlayerService {

	Player save(Player player);

	Player findById(@NotNull @Min(1) Long id);
	
	Player findByUrl(@NotNull String url);

	List<Player> findAll();

	void deleteById(@NotNull @Min(1) Long id);

}
