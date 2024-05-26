package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "PAY_GUARANTEE")
public class PayGuarantee extends AuditInfo implements Serializable {

	@Id
	@Column(name = "PAY_GUARANTEE_ID", columnDefinition = "Double(15)", nullable = false)
	private Long payGuarId;

	@Column(name = "CHRG_ID", columnDefinition = "Double(15)", nullable = false)
	private Long chrgId;

	@Column(name = "AMT", columnDefinition = "Decimal(19)", nullable = false)
	private BigDecimal amount;

	@Column(name = "GUAR_CUST_ID", columnDefinition = "Double(15)", nullable = true)
	private Long guarCustId;

	@Column(name = "GUARANTEE_REF_NR ", columnDefinition = "char(30)", nullable = true)
	private String guarRefNr;

	@Transient
	private CustomerIndex customer;

	public CustomerIndex getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerIndex customer) {
		this.customer = customer;
	}

	public Long getGuarCustId() {
		return guarCustId;
	}

	public void setGuarCustId(Long guarCustId) {
		this.guarCustId = guarCustId;
	}

	public String getGuarRefNr() {
		if (guarRefNr != null) {
			return guarRefNr.trim();
		} else
			return guarRefNr;
	}

	public void setGuarRefNr(String guarRefNr) {
		this.guarRefNr = guarRefNr;
	}

	public Long getChrgId() {
		return chrgId;
	}

	public void setChrgId(Long chrgId) {
		this.chrgId = chrgId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setPayGuarId(Long id) {
		this.payGuarId = id;
	}

	public Long getPayGuarId() {
		return payGuarId;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public PayGuarantee(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long chrgId, BigDecimal amount, Long guarCustId,
			String guarRefNr, CustomerIndex customer,Long payGuarId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.payGuarId = payGuarId;
		this.chrgId = chrgId;
		this.amount = amount;
		this.guarCustId = guarCustId;
		this.guarRefNr = guarRefNr;
		this.customer = customer;
	}

	public PayGuarantee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayGuarantee(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
