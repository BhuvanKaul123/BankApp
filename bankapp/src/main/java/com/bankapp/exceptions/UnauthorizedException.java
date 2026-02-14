package com.bankapp.exceptions;

public class UnauthorizedException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679007251509486170L;

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedException(String message) {
		super(message);
	}
}
