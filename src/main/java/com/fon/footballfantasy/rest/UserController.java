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

import com.fon.footballfantasy.domain.user.User;
import com.fon.footballfantasy.service.UserService;
import com.fon.footballfantasy.service.dto.LoginCredentials;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/login")
	ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
		return new ResponseEntity<>(userService.login(credentials), HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	ResponseEntity<?> register(@RequestBody User user) {
		return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
	}

	@PostMapping(value = "/user")
	ResponseEntity<?> save(@RequestBody User user) {
		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}

	@GetMapping(value = "/user/{id}")
	ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	ResponseEntity<?> findAll() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/user/{id}")
	ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
