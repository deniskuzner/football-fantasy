package com.fon.footballfantasy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fon.footballfantasy.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUsername(String username);
	
	@Query(value = "select favourite_club_id from users where id = :userId", nativeQuery = true)
	Long findFavouriteClubByUserId(@Param("userId") Long userId);

}
