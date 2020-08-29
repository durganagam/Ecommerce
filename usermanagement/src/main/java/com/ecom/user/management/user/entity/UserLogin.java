package com.ecom.user.management.user.entity;

import com.ecom.user.management.user.entity.model.UserStatus;

public class UserLogin {	
	private String loginId;
	private String userId;
	private UserStatus status;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}

}
