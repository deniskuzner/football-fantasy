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

import com.fon.footballfantasy.domain.Gameweek;
import com.fon.footballfantasy.service.GameweekService;

@RestController
@RequestMapping("/gameweek")
public class GameweekController {

	@Autowired
	GameweekService gameweekService;

	@PostMapping(value = "/parseSeasonGameweeks")
	ResponseEntity<?> parseSeasonGameweeks() {
		return new ResponseEntity<>(gameweekService.parseSeasonGameweeks(), HttpStatus.OK);
	}

	@PostMapping(value = "/gameweek")
	ResponseEntity<?> save(@RequestBody Gameweek gameweek) {
		return new ResponseEntity<>(gameweekService.save(gameweek), HttpStatus.OK);
	}

	@GetMapping(value = "/gameweek/{id}")
	ResponseEntity<?> findById(@PathParam("id") Long id) {
		return new ResponseEntity<>(gameweekService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/gameweek/orderNumber/{orderNumber}")
	ResponseEntity<?> findByOrderNumber(@PathParam("orderNumber") int orderNumber) {
		return new ResponseEntity<>(gameweekService.findByOrderNumber(orderNumber), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(gameweekService.findAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/gameweek/{id}")
	ResponseEntity<?> deleteById(@PathParam("id") Long id) {
		gameweekService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
