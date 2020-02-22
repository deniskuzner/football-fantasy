package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Gameweek implements Serializable {

	private static final long serialVersionUID = -2505204007816968035L;

	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private int orderNumber;
	private List<Match> matches;
	
}
