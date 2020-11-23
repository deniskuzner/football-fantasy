package com.fon.footballfantasy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fon.footballfantasy.service.TeamGameweekPerformanceService;

@RestController
@RequestMapping("/team-performances")
public class TeamGameweekPerformanceController {
	
	@Autowired
	TeamGameweekPerformanceService tgpService;
	
	@GetMapping(value = "/calculate/gameweek/{id}")
	ResponseEntity<?> calculateGameweekPoints(@PathVariable("id") Long gameweekId) {
		return new ResponseEntity<>(tgpService.calculateGameweekPoints(gameweekId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stats/{teamId}/{gameweekId}")
	ResponseEntity<?> getGameweekStats(@PathVariable("teamId") Long teamId, @PathVariable("gameweekId") Long gameweekId) {
		return new ResponseEntity<>(tgpService.getGameweekStats(teamId, gameweekId), HttpStatus.OK);
	}

}
