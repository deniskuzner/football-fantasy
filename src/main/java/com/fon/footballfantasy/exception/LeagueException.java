package com.fon.footballfantasy.exception;

public class LeagueException extends ApplicationException {
	
	private static final long serialVersionUID = -2876487550647148448L;

	public LeagueException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(code, pattern, args);
	}

	public enum LeagueExceptionCode implements ApplicationExceptionCode {
		LEAGUE_NOT_FOUND, LEAGUE_MEMBER_DUPLICATE
	}

}
