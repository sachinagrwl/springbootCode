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
@Table(name = "CUST_TERM_V")
@Subselect("select * from intermodal.cust_term_v")
@IdClass(CustomerTermPrimaryKeys.class)
public class CustomerTerm implements Serializable {

	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Id
	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = true)
	private Long terminalId;

	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;

	@Column(name = "ACCT_DSC", columnDefinition = "char(30)", nullable = true)
	private String customerDescription;

	@Column(name = "CUST_CTY", columnDefinition = "char(19)", nullable = true)
	private String customerCity;

	@Column(name = "CUST_ST_PV", columnDefinition = "char(2)", nullable = true)
	private String custormerState;

	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = true)
	private String customerNumber;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		if (customerName != null) {
			return customerName.trim();
		}
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getCustomerDescription() {
		if(customerDescription != null) {
			return customerDescription.trim();
		}
		return customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getCustomerCity() {
		if(customerCity != null) {
			return customerCity.trim();
		}
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustormerState() {
		return custormerState;
	}

	public void setCustormerState(String custormerState) {
		this.custormerState = custormerState;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerTerm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerTerm(Long customerId, Long terminalId, String customerName, String customerDescription,
			String customerCity, String custormerState, String customerNumber) {
		super();
		this.customerId = customerId;
		this.terminalId = terminalId;
		this.customerName = customerName;
		this.customerDescription = customerDescription;
		this.customerCity = customerCity;
		this.custormerState = custormerState;
		this.customerNumber = customerNumber;
	}

	public CustomerTerm(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		// TODO Auto-generated constructor stub
	}

}
