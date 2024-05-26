package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class TerminalFunctionComposite implements Serializable{

	@Column(name = "TERM_ID", columnDefinition = "double(15)", nullable = false)
	Long terminalId;
	
	@Column(name = "FUNCTION_NM", columnDefinition = "char(100)", nullable = false)
	private String functionName;
	
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public TerminalFunctionComposite(Long terminalId, String functionName) {
		super();
		this.terminalId = terminalId;
		this.functionName = functionName;
	}
	public TerminalFunctionComposite() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    

}
