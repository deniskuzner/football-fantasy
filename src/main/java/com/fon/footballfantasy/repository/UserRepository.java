package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUsername(String username);

}
