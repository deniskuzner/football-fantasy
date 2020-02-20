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
public class Goal extends MatchEvent {
	
	private static final long serialVersionUID = -1104217305755624331L;
	
	private Player goalPlayer;
	private Player assistPlayer;

}
