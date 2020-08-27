package com.ecom.user.management.user.entity;

public class User {
	private Name name;
	private String userId;
	private String mobileNo;
	
	public User() {
	}
	
	public User(Name name) {
		this.name = name;
	}
	
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
