package com.fon.footballfantasy.exception;

import static com.fon.footballfantasy.exception.InternalException.*;
import static java.lang.String.format;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -3669032535021274077L;
	
	final ApplicationExceptionCode code;
	
	public ApplicationException(ApplicationExceptionCode code, String pattern, Object... args) {
		super(format("[%s] ", code) + format(pattern, args), extractCause(args));
		this.code = code;
	}

}
