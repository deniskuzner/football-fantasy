package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.user.User;
import com.fon.footballfantasy.service.dto.LoginCredentials;

public interface UserService {
	
	User login(@NotNull LoginCredentials credentials);
	
	User register(@NotNull User user);
	
	User save(@NotNull User user);

	User findById(@NotNull @Min(1) Long id);
	
	User findByUsername(@NotNull String username);
	
	List<User> findAll();
	
	void deleteById(@NotNull @Min(1) Long id);
	
}
