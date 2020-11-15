package com.fon.footballfantasy.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class LoginCredentials {
	
	private String username;
	private String password;

}
