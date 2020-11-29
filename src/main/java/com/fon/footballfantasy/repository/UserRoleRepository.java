package com.fon.footballfantasy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.user.User;
import com.fon.footballfantasy.domain.user.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long>{
	
	List<UserRole> findByUser(User user);

}
