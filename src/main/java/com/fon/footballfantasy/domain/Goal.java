package com.fon.footballfantasy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity
@Table(name = "goals")
@PrimaryKeyJoinColumn(name = "id")
public class Goal extends MatchEvent {
	
	private static final long serialVersionUID = -1104217305755624331L;
	
	@ManyToOne
	@JoinColumn(name = "goal_player_id")
	private Player goalPlayer;
	@Column(name = "own_goal")
	private boolean ownGoal;

}
