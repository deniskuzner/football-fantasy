package com.fon.footballfantasy.exception;

public class UserException extends ApplicationException {
	
	private static final long serialVersionUID = 3399725853387715887L;

	public UserException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(code, pattern, args);
	}
	
	public enum UserExceptionCode implements ApplicationExceptionCode {
		USERNAME_ALREADY_EXISTS
	}

}
