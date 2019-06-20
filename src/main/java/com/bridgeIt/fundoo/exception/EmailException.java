package com.bridgeIt.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="EmailId invalid")
public class EmailException extends RuntimeException
{

	private static final long serialVersionUID = 1L;
	private int errorCode;
	public EmailException(String message,int errorCode) 
	{
		super(message);
		this.errorCode = errorCode;
	}
	public int getErrorCode()
	{
		return errorCode;
	}
	public void setErrorCode(int errorCode) 
	{
		this.errorCode = errorCode;
	}
	
}
