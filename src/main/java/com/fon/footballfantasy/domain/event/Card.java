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
@Table(name = "cards")
@PrimaryKeyJoinColumn(name = "id")
public class Card extends MatchEvent {

	private static final long serialVersionUID = 7192953543393167229L;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	private String card;

}
