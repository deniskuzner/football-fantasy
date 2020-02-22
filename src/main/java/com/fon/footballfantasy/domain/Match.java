package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Match implements Serializable {

	private static final long serialVersionUID = 9089264137411582677L;
	
	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private String url;
	private String result;
	private LocalDate date;
	private LocalTime time;
	private Club host;
	private Club guest;
	private String venue;
	private int gameweekOrderNumber;
	private List<MatchEvent> events;
	
}
