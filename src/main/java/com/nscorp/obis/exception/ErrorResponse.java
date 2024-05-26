package com.nscorp.obis.exception;

public class ErrorResponse {
	private String errorCode;
	private String msg;
	private Object errorElements;
	public Object getErrorElements() {
		return errorElements;
	}
	public void setErrorElements(Object errorElements) {
		this.errorElements = errorElements;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ErrorResponse(String errorCode, String msg, Object obj) {
		super();
		this.errorCode = errorCode;
		this.msg = msg;
		this.errorElements = obj;
	}
//	public ErrorResponse(String errorCode, String msg) {
//		super();
//		this.errorCode = errorCode;
//		this.msg = msg;
//	}
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
   
}
