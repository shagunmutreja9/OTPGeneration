package com.example.otpgen.otpGen.exception;

public class ApplicationResponseEntity<T> {
	private String responseCode;
	private String responseMessage;
	private T responseBody;
	
	public ApplicationResponseEntity() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ApplicationResponseEntity(String responseCode, String responseMessage, T responseBody) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseBody = responseBody;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public T getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}
	
	

}
