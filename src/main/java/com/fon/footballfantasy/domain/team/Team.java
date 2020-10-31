package com.fon.footballfantasy.domain.team;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;

	@Column(name = "total_points")
	private int totalPoints;

	@Column(name = "free_transfers")
	private int freeTransfers;

	private String name;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "captain_id")
	private Long captainId;

	@Column(name = "vice_captain_id")
	private Long viceCaptainId;

	@JsonIgnoreProperties(value = "team", allowSetters = true)
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TeamPlayer> teamPlayers;

	@JsonIgnoreProperties(value = "team", allowSetters = true)
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
	private List<TeamGameweekPerformance> teamGameweekPerformances;

}