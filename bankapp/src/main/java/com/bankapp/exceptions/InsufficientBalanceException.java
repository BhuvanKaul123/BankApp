package com.bankapp.exceptions;

public class InsufficientBalanceException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6206286002867481781L;

	public InsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientBalanceException(String message) {
		super(message);
	}
	
}
