package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.MatchEvent;

public interface MatchEventRepository extends CrudRepository<MatchEvent, Long> {

}
