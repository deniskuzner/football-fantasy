package com.fon.footballfantasy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fon.footballfantasy.domain.league.League;
import com.fon.footballfantasy.service.LeagueService;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

	@Autowired
	LeagueService leagueService;
	
	@PostMapping(value = "/league")
	ResponseEntity<?> save(@RequestBody League league) {
		return new ResponseEntity<>(leagueService.save(league), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(leagueService.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/league/{id}")
	ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(leagueService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/team/{id}")
	ResponseEntity<?> findByTeamId(@PathVariable("id") Long teamId) {
		return new ResponseEntity<>(leagueService.findByTeamId(teamId), HttpStatus.OK);
	}

	@DeleteMapping(value = "/league/{id}")
	ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		leagueService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
