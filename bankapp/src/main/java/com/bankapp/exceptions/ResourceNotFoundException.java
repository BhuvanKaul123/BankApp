package com.bankapp.exceptions;

public class ResourceNotFoundException  extends ServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5855227109422988620L;

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
