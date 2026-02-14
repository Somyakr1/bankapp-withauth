package com.bankappv2.exceptions;

public class TxIdNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxIdNotFoundException(String message) {
		super(message);
	}
}
