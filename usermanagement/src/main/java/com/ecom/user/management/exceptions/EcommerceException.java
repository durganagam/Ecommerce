package com.ecom.user.management.exceptions;

public class EcommerceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String error;

	public EcommerceException(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
