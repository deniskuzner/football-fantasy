package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Player;

public interface PlayerService {

	Player save(Player player);

	Player getById(@NotNull @Min(1) Long id);

	List<Player> getAll();

	void deleteById(@NotNull @Min(1) Long id);

}
