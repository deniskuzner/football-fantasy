package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.league.League;

public interface LeagueRepository extends CrudRepository<League, Long> {

}
