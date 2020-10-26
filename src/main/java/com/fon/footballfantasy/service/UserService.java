package com.fon.footballfantasy.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fon.footballfantasy.domain.User;

public interface UserService {
	
	User login(User user);
	
	User save(@NotNull User user);

	User findById(@NotNull @Min(1) Long id);
	
	List<User> findAll();
	
	void deleteById(@NotNull @Min(1) Long id);
	
}
