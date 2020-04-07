package com.fon.footballfantasy.exception;

public class MatchException extends ApplicationException {

	private static final long serialVersionUID = 291000748431750591L;

	public MatchException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(code, pattern, args);
	}

	public enum MatchExceptionCode implements ApplicationExceptionCode {
		CLUBS_NOT_FOUND
	}

}
