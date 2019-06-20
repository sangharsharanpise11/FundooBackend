package com.bridgeIt.fundoo.response;

public class Response {
	private int statusCode;
	private String statusMessage;
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
	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", statusmessage=" + statusMessage + "]";
	}
	
	
}
