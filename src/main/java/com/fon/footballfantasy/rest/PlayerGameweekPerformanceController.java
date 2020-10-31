package com.fon.footballfantasy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fon.footballfantasy.service.PlayerGameweekPerformanceService;
import com.fon.footballfantasy.service.dto.MatchSearchRequest;

@RestController
@RequestMapping("/performances")
public class PlayerGameweekPerformanceController {
	
	@Autowired
	PlayerGameweekPerformanceService performanceService;
	
	@PostMapping(value = "/calculate/date")
	ResponseEntity<?> calculateByDate(@RequestBody MatchSearchRequest searchRequest) {
		return new ResponseEntity<>(performanceService.calculateByDate(searchRequest), HttpStatus.OK);
	}
	
	@GetMapping(value = "/calculate/gameweek/{id}")
	ResponseEntity<?> calculateByGameweek(@PathVariable("id") Long gameweekId) {
		return new ResponseEntity<>(performanceService.calculateByGameweek(gameweekId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/player/{id}")
	ResponseEntity<?> findByPlayerId(@PathVariable("id") Long playerId) {
		return new ResponseEntity<>(performanceService.findByPlayerId(playerId), HttpStatus.OK);
	}

}
