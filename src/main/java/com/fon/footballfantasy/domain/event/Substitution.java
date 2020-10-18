package com.fon.footballfantasy.domain.event;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fon.footballfantasy.domain.Player;

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
@Table(name = "substitutions")
@PrimaryKeyJoinColumn(name = "id")
public class Substitution extends MatchEvent {
	
	private static final long serialVersionUID = 1410704224587036385L;
	
	@ManyToOne
	@JoinColumn(name = "in_player_id")
	private Player inPlayer;
	@ManyToOne
	@JoinColumn(name = "out_player_id")
	private Player outPlayer;

}
