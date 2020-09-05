package com.ecom.webportal.registration.entity;

public class UserSignUpRequest {

	private Name name;
	private String mobileNo;

	public UserSignUpRequest() {
	}

	public UserSignUpRequest(final Name name, final String mobileNo) {
		this.name = name;
		this.mobileNo = mobileNo;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
