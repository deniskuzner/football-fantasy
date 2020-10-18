package com.fon.footballfantasy.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserTeamGameweekPerformance {

	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private int points;
	private UserTeam userTeam;
	private Gameweek gameweek;
	
}
