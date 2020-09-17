package com.fon.footballfantasy.service.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class MatchSearchRequest {

	@NotNull
	LocalDateTime fromDate;

	@NotNull
	LocalDateTime toDate;

}
