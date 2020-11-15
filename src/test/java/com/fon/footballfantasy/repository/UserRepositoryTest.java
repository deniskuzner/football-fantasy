package com.fon.footballfantasy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fon.footballfantasy.domain.Club;
import com.fon.footballfantasy.domain.User;
import com.fon.footballfantasy.setup.ClubSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserRepositoryTest extends BaseRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClubSetup clubSetup;

	@Test
	void testCrud() {

		log.info("Test crud");
		List<Club> clubs = clubSetup.getSetup();
		User user = User.builder().username("pera").password("pera").firstName("Pera").lastName("Peric")
				.email("pera@gmail.com").gender("MALE").birthDate("20-20-2020").phoneNumber("06123456789")
				.favouriteClubId(clubs.get(0).getId()).build();

		log.info("Save user");
		userRepository.save(user);

		log.info("Find user by ID");
		User u = userRepository.findById(user.getId()).get();
		assertNotNull(u);
		assertEquals(user.getId(), u.getId());
		assertEquals(user.getUsername(), u.getUsername());
		assertEquals(user.getPassword(), u.getPassword());
		assertEquals(user.getFirstName(), u.getFirstName());
		assertEquals(user.getLastName(), u.getLastName());
		assertEquals(user.getFavouriteClubId(), clubs.get(0).getId());

	}
	
	@Test
	void testFindByUsernameAndPassword() {
		
		List<Club> clubs = clubSetup.getSetup();
		User user = User.builder().username("pera").password("pera").firstName("Pera").lastName("Peric")
				.email("pera@gmail.com").gender("MALE").birthDate("20-20-2020").phoneNumber("06123456789")
				.favouriteClubId(clubs.get(0).getId()).build();

		log.info("Save user");
		userRepository.save(user);

		log.info("Find user by username and password");
		User u = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		assertNotNull(user);
		assertEquals(user.getId(), u.getId());
		assertEquals(user.getUsername(), u.getUsername());
		assertEquals(user.getPassword(), u.getPassword());
		assertEquals(user.getFirstName(), u.getFirstName());
		assertEquals(user.getLastName(), u.getLastName());
		assertEquals(user.getFavouriteClubId(), clubs.get(0).getId());
		
		log.info("Find user with wrong username and password");
		u = userRepository.findByUsernameAndPassword("greska", "greska");
		assertNull(u);
		
	}
	
	@AfterEach
	void deleteAll() {
		log.info("Delete all users");
		userRepository.deleteAll();
		clubSetup.deleteAll();
		assertEquals(0, ((List<User>) userRepository.findAll()).size());
	}

}
