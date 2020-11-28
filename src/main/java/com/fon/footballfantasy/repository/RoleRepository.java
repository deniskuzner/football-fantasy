package com.fon.footballfantasy.repository;

import org.springframework.data.repository.CrudRepository;

import com.fon.footballfantasy.domain.user.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByName(String name);
	
}
