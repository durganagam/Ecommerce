package com.ecom.user.management.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.ecom.user.management.user.entity.model.UserStatus;

@Entity
public class UserLogin {
	@Column
	private String loginId;
	@Id
	private String userId;
	@Column
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
