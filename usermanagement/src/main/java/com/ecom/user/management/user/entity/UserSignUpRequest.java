package com.ecom.user.management.user.entity;

public class UserSignUpRequest {
	private Name name;
	private String mobileNo;
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
