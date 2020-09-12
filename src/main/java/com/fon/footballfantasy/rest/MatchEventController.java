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

import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.service.MatchEventService;

@RestController
@RequestMapping("/match-events")
public class MatchEventController {

	@Autowired
	MatchEventService matchEventService;

	@GetMapping(value = "/parse-match-events/gameweek/{id}")
	ResponseEntity<?> parseMatchEventsByGameweekId(@PathVariable("id") Long gameweekId) {
		// TODO: IF NULL VRATITI GRESKU
		return new ResponseEntity<>(matchEventService.parseMatchEventsByGameweekId(gameweekId), HttpStatus.OK);
	}

	@PostMapping(value = "/parse-match-events/match/{url}")
	ResponseEntity<?> parseMatchEventsByMatchUrl(@PathVariable("url") String matchUrl) {
		return new ResponseEntity<>(matchEventService.parseMatchEventsByMatchUrl(matchUrl), HttpStatus.OK);
	}

	@PostMapping(value = "/match-event")
	ResponseEntity<?> save(@RequestBody MatchEvent matchEvent) {
		return new ResponseEntity<>(matchEventService.save(matchEvent), HttpStatus.OK);
	}

	@GetMapping(value = "/match-event/{id}")
	ResponseEntity<?> findByMatchId(@PathVariable("id") Long matchId) {
		return new ResponseEntity<>(matchEventService.findByMatchId(matchId), HttpStatus.OK);
	}

	@DeleteMapping(value = "/match-event/{id}")
	ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		matchEventService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}