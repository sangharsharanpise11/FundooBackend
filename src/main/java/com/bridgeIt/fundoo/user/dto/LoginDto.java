package com.bridgeIt.fundoo.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class LoginDto
{
	@Column(name = "emailId", nullable = false)
	@NotNull(message="email is Requered")
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	@NotEmpty(message="Please provide valid emailId")
	private String emailId;

	@NotNull(message="password is Requered")
    @NotEmpty(message="Please provide password")
	@Column(name="password")
	private String password;

	public LoginDto()
	{
	
	}

	public String getemailId() {
		return emailId;
	}

	public void setemailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDto [emailId=" + emailId + ", password=" + password + "]";
	}
	
}

