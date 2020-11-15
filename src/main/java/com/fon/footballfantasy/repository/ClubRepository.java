package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.Club;

public interface ClubRepository extends CrudRepository<Club, Long> {
	
	Club findByUrl(String url);

	@Query(value = "select name from clubs", nativeQuery = true)
	List<String> findAllNames();

}
