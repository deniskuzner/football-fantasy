package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.Match;

public interface MatchService {

	Match save(Match match);

	Match findById(@NotNull @Min(1) Long id);

	Match findByUrl(@NotNull String url);

	List<Match> findAll();

	void deleteById(@NotNull @Min(1) Long id);
	
}
