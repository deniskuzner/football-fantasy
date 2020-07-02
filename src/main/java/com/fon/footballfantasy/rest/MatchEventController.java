package com.fon.footballfantasy.rest;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fon.footballfantasy.domain.MatchEvent;
import com.fon.footballfantasy.service.MatchEventService;

@RestController
@RequestMapping("/matchEvents")
public class MatchEventController {

	@Autowired
	MatchEventService matchEventService;

	@PostMapping(value = "/parseMatchEvents/gameweek/{id}")
	ResponseEntity<?> parseMatchEventsByGameweekId(@PathParam("id") Long gameweekId) {
		return new ResponseEntity<>(matchEventService.parseMatchEventsByGameweekId(gameweekId), HttpStatus.OK);
	}

	@PostMapping(value = "/parseMatchEvents/match/{url}")
	ResponseEntity<?> parseMatchEventsByMatchUrl(@PathParam("url") String matchUrl) {
		return new ResponseEntity<>(matchEventService.parseMatchEventsByMatchUrl(matchUrl), HttpStatus.OK);
	}

	@PostMapping(value = "/matchEvent")
	ResponseEntity<?> save(@RequestBody MatchEvent matchEvent) {
		return new ResponseEntity<>(matchEventService.save(matchEvent), HttpStatus.OK);
	}

	@GetMapping(value = "/matchEvent/{id}")
	ResponseEntity<?> findByMatchId(@PathParam("id") Long matchId) {
		return new ResponseEntity<>(matchEventService.findByMatchId(matchId), HttpStatus.OK);
	}

	@DeleteMapping(value = "/matchEvent/{id}")
	ResponseEntity<?> deleteById(@PathParam("id") Long id) {
		matchEventService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
