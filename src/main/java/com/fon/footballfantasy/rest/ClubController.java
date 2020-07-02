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

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.service.ClubService;

@RestController
@RequestMapping("/clubs")
public class ClubController {

	@Autowired
	ClubService clubService;

	@PostMapping(value = "/parseSeasonClubs")
	ResponseEntity<?> parseSeasonClubs() {
		return new ResponseEntity<>(clubService.parseSeasonClubs(), HttpStatus.OK);
	}

	@PostMapping(value = "/club")
	ResponseEntity<?> save(@RequestBody Club club) {
		return new ResponseEntity<>(clubService.save(club), HttpStatus.OK);
	}

	@GetMapping(value = "/club/{id}")
	ResponseEntity<?> findById(@PathParam("id") Long id) {
		return new ResponseEntity<>(clubService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(clubService.findAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/club/{id}")
	ResponseEntity<?> deleteById(@PathParam("id") Long id) {
		clubService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
