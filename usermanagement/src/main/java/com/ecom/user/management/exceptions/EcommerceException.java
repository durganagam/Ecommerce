package com.ecom.user.management.exceptions;

public class EcommerceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String error;
	private int statusCode;
	private String errorCode;

	public EcommerceException(String error) {
		this.error = error;
	}

	public EcommerceException(String error, int statusCode, String errorCode) {
		super();
		this.error = error;
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

}
