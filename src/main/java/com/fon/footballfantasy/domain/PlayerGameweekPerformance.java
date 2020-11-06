package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "player_gameweek_performances")
public class PlayerGameweekPerformance implements Serializable {
	
	private static final long serialVersionUID = 5407747763074017323L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	@JsonIgnoreProperties(value = "playerGameweekPerformances", allowSetters = true)
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	@JsonIgnoreProperties(value = {"matches", "playerGameweekPerformances"}, allowSetters = true)
	@ManyToOne
	@JoinColumn(name = "gameweek_id")
	private Gameweek gameweek;
	private int points;
//	@OneToMany(cascade = CascadeType.REMOVE)
//	@JoinColumn(name = "player_gameweek_performance_id", updatable = false)
//	private List<PlayerStatistic> statistics;
	
}
