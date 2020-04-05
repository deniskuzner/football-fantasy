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
public class MinutesPlayed extends MatchEvent {

	private static final long serialVersionUID = 5545281229612088023L;
	
	private Player player;
	private int minutesPlayed;

}
