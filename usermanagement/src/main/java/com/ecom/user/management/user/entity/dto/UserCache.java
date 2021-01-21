package com.ecom.user.management.user.entity.dto;

import java.time.LocalDateTime;

import com.ecom.user.management.user.entity.Name;

public class UserCache {

	private Name name;
	private String oneTimePassword;
	private LocalDateTime otpCreatedDate;
	private String mobileNumber;

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public LocalDateTime getOtpCreatedDate() {
		return otpCreatedDate;
	}

	public void setOtpCreatedDate(LocalDateTime otpCreatedDate) {
		this.otpCreatedDate = otpCreatedDate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
