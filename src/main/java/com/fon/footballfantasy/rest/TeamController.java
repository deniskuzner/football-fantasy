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

import com.fon.footballfantasy.domain.team.Team;
import com.fon.footballfantasy.service.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {

	@Autowired
	TeamService teamService;

	@PostMapping(value = "/team")
	ResponseEntity<?> save(@RequestBody Team team) {
		return new ResponseEntity<>(teamService.save(team), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/team/{id}")
	ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(teamService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/user/{id}")
	ResponseEntity<?> findByUserId(@PathVariable("id") Long userId) {
		return new ResponseEntity<>(teamService.findByUserId(userId), HttpStatus.OK);
	}
	
}
