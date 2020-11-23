package com.fon.footballfantasy.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class TeamGameweekStats {

	private int rank;
	private int averagePoints;
	private int highestPoints;
	
}
