package com.fon.footballfantasy.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.User;
import com.fon.footballfantasy.repository.UserRepository;
import com.fon.footballfantasy.service.UserService;

@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User login(User user) {
		User u = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(u == null) {
			//TODO: UMESTO NULL BACITI CUSTOM LOGIN EXCEPTION
			return null;
		}
		return u;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	
}
