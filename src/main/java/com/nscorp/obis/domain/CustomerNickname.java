package com.nscorp.obis.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@SuppressWarnings("serial")
@Entity
@Immutable
@Table(name = "CUST_NICKNAME")
@Subselect("select * from intermodal.cust_nickname")
@IdClass(CustomerNicknamePrimaryKeys.class)
public class CustomerNickname extends AuditInfo implements Serializable {

	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Id
	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = false)
	private Long terminalId;

	@Id
	@Column(name = "NICKNAME", columnDefinition = "char(35)", nullable = false)
	private String customerNickname;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public CustomerNickname() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerNickname(Long customerId, Long terminalId, String customerNickname) {
		super();
		this.customerId = customerId;
		this.terminalId = terminalId;
		this.customerNickname = customerNickname;
	}

	public CustomerNickname(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		// TODO Auto-generated constructor stub
	}

}
