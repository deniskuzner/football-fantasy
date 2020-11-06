package com.fon.footballfantasy.exception;

public class GameweekException extends ApplicationException {

	private static final long serialVersionUID = 4820556255950245538L;
	
	public GameweekException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(code, pattern, args);
	}

	public enum GameweekExceptionCode implements ApplicationExceptionCode {
		GAMEWEEK_NOT_FOUND
	}

}
