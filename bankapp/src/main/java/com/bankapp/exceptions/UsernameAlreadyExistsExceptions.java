package com.bankapp.exceptions;

public class UsernameAlreadyExistsExceptions extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1302594374082990771L;

	public UsernameAlreadyExistsExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameAlreadyExistsExceptions(String message) {
		super(message);
	}
}
