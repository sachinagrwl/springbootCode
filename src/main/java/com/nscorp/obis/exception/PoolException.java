package com.nscorp.obis.exception;

public class PoolException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String str;
	private Object obj;
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	public PoolException(String s) {
		super(s);
		this.str = s;
		
	}
	public PoolException(String s, Object obj) {
		super(s);
		this.str = s;
		this.obj = obj;
	}	
	public PoolException(String code,String s, Object obj) {
		super(s);
		this.code=code;
		this.str = s;
		this.obj = obj;
	}	

	public PoolException() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
    
}
