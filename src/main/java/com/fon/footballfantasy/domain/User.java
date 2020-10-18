package com.fon.footballfantasy.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class User {
	
	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String birthDate;
	private String phoneNumber;
	private String country;
	private Club favouriteClub;

}
