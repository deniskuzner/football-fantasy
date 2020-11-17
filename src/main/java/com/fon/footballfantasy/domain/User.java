package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fon.footballfantasy.domain.team.Team;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 7964900004177332584L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	private String username;
	private String password;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String email;
	private String gender;
	@Column(name = "birth_date")
	private String birthDate;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "favourite_club_id")
	private Long favouriteClubId;
	@OneToOne(mappedBy = "user")
	private Team team;

}
