package com.ecom.user.management.user.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Name {
	private String lastName;
	private String firstName;
	private String middleName;

	public Name() {
	}

	public Name(String lastName, String firstName, String middleName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
}
