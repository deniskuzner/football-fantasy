package com.fon.footballfantasy.exception;

public class TeamGameweekPerformanceException extends ApplicationException {

	private static final long serialVersionUID = -1816711665902247488L;

	public TeamGameweekPerformanceException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(code, pattern, args);
	}
	
	public enum TeamGameweekPerformanceExceptionCode implements ApplicationExceptionCode {
		PLAYER_POINTS_NOT_CALCULATED
	}

}
