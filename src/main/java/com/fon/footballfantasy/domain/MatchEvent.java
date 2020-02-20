package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class MatchEvent implements Serializable {

	private static final long serialVersionUID = 5506326865651812068L;
	
	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private Long matchId;
	private int minute;
	private Club club;
	
}
