package com.bridgeIt.fundoo.notes.dto;


public class CollaboratorDto 
{
  private String emailId;

@Override
public String toString() {
	return "CollaboratorDto [emailId=" + emailId + "]";
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}
  
}
