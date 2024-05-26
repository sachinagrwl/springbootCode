package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "CUST_TERM")
@IdClass(CustomerTermPrimaryKeys.class)
public class CustomerTerminal extends AuditInfo{
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Id
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

	public CustomerTerminal(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long customerId, Long terminalId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.customerId = customerId;
		this.terminalId = terminalId;
	}

	public CustomerTerminal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerTerminal(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	

}
