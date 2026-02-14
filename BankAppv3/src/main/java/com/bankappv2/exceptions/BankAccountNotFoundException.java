package com.bankappv2.exceptions;

public class BankAccountNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 6115120847499794478L;

	public BankAccountNotFoundException(String message) {
		super(message);
	}
}
