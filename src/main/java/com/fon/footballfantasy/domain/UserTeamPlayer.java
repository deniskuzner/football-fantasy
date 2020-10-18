package com.fon.footballfantasy.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserTeamPlayer {
	
	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private int points;
	@Column(name = "on_bench")
	private boolean onBench;
	private Player player;
	private UserTeam userTeam;

}
