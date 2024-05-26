package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class CustomerLocalInfoPrimaryKeys implements Serializable {
	
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = true)
	private Long terminalId;
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public CustomerLocalInfoPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerLocalInfoPrimaryKeys(Long customerId, Long terminalId) {
		super();
		this.customerId = customerId;
		this.terminalId = terminalId;
	}

}
