package com.fon.footballfantasy.domain;

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
public class PlayerGameweekPerformance {
	
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
	
	public boolean isHost(Match match, Club club) {
		return club.getId().equals(match.getHost().getId());
	}

	public boolean isGuest(Match match, Club club) {
		return club.getId().equals(match.getGuest().getId());
	}
	
}