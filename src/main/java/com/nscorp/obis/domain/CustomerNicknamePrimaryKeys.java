package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class CustomerNicknamePrimaryKeys implements Serializable {
	
	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = false)
	private Long terminalId;

	@Column(name = "NICKNAME", columnDefinition = "char(35)", nullable = false)
	private String customerNickname;

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getCustomerNickname() {
		if (customerNickname != null) {
			return customerNickname.trim();
		}
		return customerNickname;
	}

	public void setCustomerNickname(String customerNickname) {
		this.customerNickname = customerNickname;
	}

	public CustomerNicknamePrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerNicknamePrimaryKeys(Long terminalId, String customerNickname) {
		super();
		this.terminalId = terminalId;
		this.customerNickname = customerNickname;
	}
	
}
