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

import com.fon.footballfantasy.domain.Player;
import com.fon.footballfantasy.service.PlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	@PostMapping(value = "/player")
	ResponseEntity<?> save(@RequestBody Player player) {
		return new ResponseEntity<>(playerService.save(player), HttpStatus.OK);
	}

	@GetMapping(value = "/player/{id}")
	ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/player/{id}")
	ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		playerService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/all/points-desc")
	ResponseEntity<?> findAllOrderByPointsDesc() {
		return new ResponseEntity<>(playerService.findAllOrderByPointsDesc(), HttpStatus.OK);
	}

}
