package com.fon.footballfantasy.service.impl;

import static com.fon.footballfantasy.exception.UserException.UserExceptionCode.LOGIN_FAILED;
import static com.fon.footballfantasy.exception.UserException.UserExceptionCode.USERNAME_ALREADY_EXISTS;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fon.footballfantasy.domain.user.Role;
import com.fon.footballfantasy.domain.user.User;
import com.fon.footballfantasy.domain.user.UserRole;
import com.fon.footballfantasy.exception.UserException;
import com.fon.footballfantasy.repository.RoleRepository;
import com.fon.footballfantasy.repository.UserRepository;
import com.fon.footballfantasy.repository.UserRoleRepository;
import com.fon.footballfantasy.service.UserService;
import com.fon.footballfantasy.service.dto.LoginCredentials;

@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public User login(LoginCredentials credentials) {
		User u = userRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
		if(u == null) {
			throw new UserException(LOGIN_FAILED, "Invalid username or password!");
		}
		return u;
	}
	
	@Override
	public User register(User user) {
		User u = userRepository.findByUsername(user.getUsername());
		if(u != null) {
			throw new UserException(USERNAME_ALREADY_EXISTS, "Username %s is already taken!", user.getUsername());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role roleUser = roleRepository.findByName("USER");
		user.setRoles(Arrays.asList(
				UserRole.builder().user(user).role(roleUser).build()
				)
		);
		return userRepository.save(user);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		User user = userRepository.findById(id).get();
		if(user != null) {
			user.setRoles(userRoleRepository.findByUser(user));
		}
		return user;
	}
	
	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user != null) {
			user.setRoles(userRoleRepository.findByUser(user));
		}
		return user;
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
