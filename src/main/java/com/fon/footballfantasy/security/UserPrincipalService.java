package com.fon.footballfantasy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fon.footballfantasy.domain.user.User;
import com.fon.footballfantasy.repository.UserRepository;
import com.fon.footballfantasy.repository.UserRoleRepository;

@Service
public class UserPrincipalService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Username %s not found!", username));
		}
		user.setRoles(userRoleRepository.findByUser(user));
		return new UserPrincipal(user);
	}
	
}
