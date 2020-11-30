package com.fon.footballfantasy.service.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AuthData {
	
	private Long userId;
	private Long teamId;
	private String roles;

}
