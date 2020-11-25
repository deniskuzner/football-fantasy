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

import com.fon.footballfantasy.domain.league.TeamLeagueMembership;
import com.fon.footballfantasy.service.TeamLeagueMembershipService;

@RestController
@RequestMapping("/team-league-memberships")
public class TeamLeagueMembershipController {
	
	@Autowired
	TeamLeagueMembershipService tlmService;
	
	@PostMapping(value = "/tlm")
	ResponseEntity<?> save(@RequestBody TeamLeagueMembership tlm) {
		return new ResponseEntity<>(tlmService.save(tlm), HttpStatus.OK);
	}

	@GetMapping(value = "/tlm/{id}")
	ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(tlmService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/team/{id}")
	ResponseEntity<?> findByTeamId(@PathVariable("id") Long teamId) {
		return new ResponseEntity<>(tlmService.findByTeamId(teamId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/league/{id}")
	ResponseEntity<?> findByLeagueId(@PathVariable("id") Long leagueId) {
		return new ResponseEntity<>(tlmService.findByLeagueId(leagueId), HttpStatus.OK);
	}

	@DeleteMapping(value = "/tlm/{id}")
	ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		tlmService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/team/{teamId}/league/{leagueId}")
	ResponseEntity<?> deleteByTeamAndLeague(@PathVariable("teamId") Long teamId, @PathVariable("leagueId") Long leagueId) {
		tlmService.deleteByTeamAndLeague(teamId, leagueId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
