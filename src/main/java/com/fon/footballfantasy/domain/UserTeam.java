package com.fon.footballfantasy.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserTeam {

	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;

	private int totalPoints;
	private int freeTransfers;
	private String name;
	private Player captain;
	private Player viceCaptain;
	private User user;
	private List<UserTeamPlayer> teamPlayers;
	private List<UserTeamGameweekPerformance> teamGameweekPerformances;
	
}
