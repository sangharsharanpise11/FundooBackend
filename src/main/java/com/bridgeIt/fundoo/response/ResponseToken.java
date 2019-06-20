package com.bridgeIt.fundoo.response;

public class ResponseToken 
{
	private int statusCode;
	private String statusMessage;
	private String Token;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusmessage) {
		this.statusMessage = statusmessage;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	@Override
	public String toString()
	{
		return "ResponseToken [statusCode=" + statusCode + ", statusmessage=" + statusMessage + ", Token=" + Token
				+ "]";
	}
 
	
}