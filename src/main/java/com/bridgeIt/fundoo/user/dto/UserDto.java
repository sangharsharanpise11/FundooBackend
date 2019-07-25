package com.bridgeIt.fundoo.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
//import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UserDto 
{
	
	@NotEmpty(message = "Please Enter FirstName")
	@NotNull(message="Please enter valid name")
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@NotEmpty(message = "Please Enter LastName")
	@NotNull(message="Please enter valid name")
	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "emailId", nullable = false)
	@Email(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	@NotEmpty(message = "Please provide valid EmailId")
	@NotNull(message="Please enter valid EmailId")
	private String emailId;

	@Column(name = "mobNo")
	@NotNull(message="Please enter valid number")
	@NotEmpty(message="Please enter valid number")
	@Pattern(regexp = "[0-9]{10}", message = "provide valid mobile number")
	private String mobNo;

//	@NotEmpty(message = "Please provide gender")
//	@NotNull(message="Please enter gender")
//	@Length(min = 6, max = 50, message = "password must be at least 6 character and max 50 character")
//	@Column(name = "gender")
//	private String gender;
	
	@NotEmpty(message = "Please provide password")
	@NotNull(message="Please enter valid Password")
	@Column(name = "password")
	private String password;

	public UserDto() {
		super();
	}

	public UserDto(String firstName, String lastName, String emailId, String mobNo,String gender, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobNo = mobNo;
		//this.gender=gender;
		this.password = password;
	}

//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", mobNo="
				+ mobNo + ", password=" + password + "]";
	}

}
