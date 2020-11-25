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

import com.fon.footballfantasy.domain.Match;
import com.fon.footballfantasy.service.MatchService;

@RestController
@RequestMapping("/matches")
public class MatchController {

	@Autowired
	MatchService matchService;

	@PostMapping(value = "/match")
	ResponseEntity<?> save(@RequestBody Match match) {
		return new ResponseEntity<>(matchService.save(match), HttpStatus.OK);
	}

	@GetMapping(value = "/match/{id}")
	ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(matchService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/match/url/{url}")
	ResponseEntity<?> findByUrl(@PathVariable("url") String url) {
		return new ResponseEntity<>(matchService.findByUrl(url), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(matchService.findAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/match/{id}")
	ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		matchService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/gameweek/order-number/{orderNumber}")
	ResponseEntity<?> findByGameweekOrderNumber(@PathVariable("orderNumber") int gameweekOrderNumber) {
		return new ResponseEntity<>(matchService.findByGameweekOrderNumber(gameweekOrderNumber), HttpStatus.OK);
	}
	
}
