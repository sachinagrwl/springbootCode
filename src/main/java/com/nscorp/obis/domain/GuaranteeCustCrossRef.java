package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "GUAR_CUST_XRF")
@IdClass(GuaranteeCustCrossRefPrimaryKeys.class)
@EqualsAndHashCode(callSuper = false)
public class GuaranteeCustCrossRef extends AuditInfo {

	@Id
	@Column(name = "GUAR_CUST_XRF_ID", columnDefinition = "double", nullable = false)
	private Long guaranteeCustXrefId;

	@Id
	@Column(name = "CORP_CUST_ID", columnDefinition = "double", nullable = false)
	private Long corpCustId;

	@Column(name = "GUAR_LONG_NM", columnDefinition = "char(30)", nullable = false)
	private String guaranteeCustLongName;

	@Column(name = "TERM_NM", columnDefinition = "char(30)", nullable = false)
	private String terminalName;

	@Column(name = "TERM_ID", columnDefinition = "double", nullable = false)
	private Long terminalId;

	@Column(name = "GUAR_CUST_NR", columnDefinition = "char(30)", nullable = false)
	private String guaranteeCustomerNumber;

	@Column(name = "CUST_ID", columnDefinition = "double", nullable = false)
	private Long customerId;

	public Long getGuaranteeCustXrefId() {
		return guaranteeCustXrefId;
	}

	public void setGuaranteeCustXrefId(Long guaranteeCustXrefId) {
		this.guaranteeCustXrefId = guaranteeCustXrefId;
	}

	public Long getCorpCustId() {
		return corpCustId;
	}

	public void setCorpCustId(Long corpCustId) {
		this.corpCustId = corpCustId;
	}

	public String getGuaranteeCustLongName() {
		return StringUtils.isNotBlank(guaranteeCustLongName) ? guaranteeCustLongName.trim() : guaranteeCustLongName;
	}

	public void setGuaranteeCustLongName(String guaranteeCustLongName) {

		this.guaranteeCustLongName = StringUtils.isNotBlank(guaranteeCustLongName) ? guaranteeCustLongName.trim()
				: guaranteeCustLongName;
	}

	public String getTerminalName() {
		return StringUtils.isNotBlank(terminalName) ? terminalName.trim() : terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = StringUtils.isNotBlank(terminalName) ? terminalName.trim() : terminalName;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getGuaranteeCustomerNumber() {
		return StringUtils.isNotBlank(guaranteeCustomerNumber) ? guaranteeCustomerNumber.trim()
				: guaranteeCustomerNumber;
	}

	public void setGuaranteeCustomerNumber(String guaranteeCustomerNumber) {
		this.guaranteeCustomerNumber = StringUtils.isNotBlank(guaranteeCustomerNumber) ? guaranteeCustomerNumber.trim()
				: guaranteeCustomerNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public GuaranteeCustCrossRef(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long guaranteeCustXrefId, Long corpCustId,
			String guaranteeCustLongName, String terminalName, Long terminalId, String guaranteeCustomerNumber,
			Long customerId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.guaranteeCustXrefId = guaranteeCustXrefId;
		this.corpCustId = corpCustId;
		this.guaranteeCustLongName = guaranteeCustLongName;
		this.terminalName = terminalName;
		this.terminalId = terminalId;
		this.guaranteeCustomerNumber = guaranteeCustomerNumber;
		this.customerId = customerId;
	}

}