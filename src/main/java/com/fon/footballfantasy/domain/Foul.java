package com.fon.footballfantasy.domain;

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
public class Foul extends MatchEvent {

	private static final long serialVersionUID = 7192953543393167229L;
	
	private Player player;
	private String card;

}
