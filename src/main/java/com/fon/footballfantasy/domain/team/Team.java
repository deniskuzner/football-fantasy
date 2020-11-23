package com.fon.footballfantasy.domain.team;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fon.footballfantasy.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "teams")
public class Team implements Serializable {

	private static final long serialVersionUID = -4767733499731120287L;

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
	
	@Column(name = "money_remaining")
	private double moneyRemaining;

	@Column(name = "captain_id")
	private Long captainId;

	@Column(name = "vice_captain_id")
	private Long viceCaptainId;

	@JsonIgnoreProperties(value = "team", allowSetters = true)
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnoreProperties(value = "team", allowSetters = true)
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	private List<TeamPlayer> teamPlayers;

	@JsonIgnoreProperties(value = "team", allowSetters = true)
	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
	private List<TeamGameweekPerformance> teamGameweekPerformances;

}
