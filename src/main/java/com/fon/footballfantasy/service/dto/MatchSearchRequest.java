package com.fon.footballfantasy.service.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class MatchSearchRequest {

	@NotNull
	Date fromDate;

	@NotNull
	Date toDate;

}
