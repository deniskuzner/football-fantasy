package com.fon.footballfantasy.exception;

public class TeamException extends ApplicationException {

	private static final long serialVersionUID = -2351826701300944901L;

	public TeamException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(code, pattern, args);
	}
	
	public enum TeamExceptionCode implements ApplicationExceptionCode {
		TEAM_NOT_VALID
	}

}
