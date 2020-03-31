package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
