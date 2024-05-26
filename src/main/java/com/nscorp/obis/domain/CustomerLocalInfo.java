package com.nscorp.obis.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CUST_LOC_INFO")
@IdClass(CustomerLocalInfoPrimaryKeys.class)
public class CustomerLocalInfo extends AuditInfo implements Serializable {

	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Id
	@Column(name = "TERM_ID", columnDefinition = "Double(15)", nullable = false)
	private Long terminalId;

	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;

	@Column(name = "CUST_ADDR_1", columnDefinition = "char(35)", nullable = true)
	private String custAddr1;

	@Column(name = "CUST_ADDR_2", columnDefinition = "char(35)", nullable = true)
	private String custAddr2;

	@Column(name = "CUST_CTY", columnDefinition = "char(19)", nullable = true)
	private String customerCity;

	@Column(name = "CUST_ZIP_CD", columnDefinition = "char(10)", nullable = true)
	private String custZipCd;

	@Column(name = "CUST_ST_PV", columnDefinition = "char(2)", nullable = true)
	private String customerState;

	@Column(name = "CUST_CNTRY", columnDefinition = "char(3)", nullable = true)
	private CustomerCountry custCountry;

	@Column(name = "LOCAL_CONTACT", columnDefinition = "char(30)", nullable = true)
	private String localContact;

	@Column(name = "FAX_AREA_CD", columnDefinition = "SMALLINT", nullable = true)
	private Integer faxAreaCd;

	@Column(name = "FAX_EXCH", columnDefinition = "SMALLINT", nullable = true)
	private Integer faxExch;

	@Column(name = "FAX_EXT", columnDefinition = "SMALLINT", nullable = true)
	private Integer faxExt;

	@Column(name = "CUST_AREA_CD", columnDefinition = "SMALLINT", nullable = true)
	private Integer custAreaCd;

	@Column(name = "CUST_EXCH", columnDefinition = "SMALLINT", nullable = true)
	private Integer custExch;

	@Column(name = "CUST_EXT", columnDefinition = "SMALLINT", nullable = true)
	private Integer custExt;

	@Column(name = "ADDTL_INFO", columnDefinition = "char(4)", nullable = true)
	private String addtlInfo;

	@Column(name = "PHONE_BASE", columnDefinition = "char(4)", nullable = true)
	private String phoneBase;

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

	public String getCustomerName() {
		if (customerName != null) {
			return customerName.trim();
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustAddr1() {
		if (custAddr1 != null) {
			return custAddr1.trim();
		}
		return custAddr1;
	}

	public void setCustAddr1(String custAddr1) {
		this.custAddr1 = custAddr1;
	}

	public String getCustAddr2() {
		if (custAddr2 != null) {
			return custAddr2.trim();
		}
		return custAddr2;
	}

	public void setCustAddr2(String custAddr2) {
		this.custAddr2 = custAddr2;
	}

	public String getCustomerCity() {
		if (customerCity != null) {
			return customerCity.trim();
		}
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustZipCd() {
		if (custZipCd != null) {
			return custZipCd.trim();
		}
		return custZipCd;
	}

	public void setCustZipCd(String custZipCd) {
		this.custZipCd = custZipCd;
	}

	public String getCustomerState() {
		if (customerState != null) {
			return customerState.trim();
		}
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public CustomerCountry getCustCountry() {
		return custCountry;
	}

	public void setCustCountry(CustomerCountry custCountry) {
		this.custCountry = custCountry;
	}

	public String getLocalContact() {
		if (localContact != null) {
			return localContact.trim();
		}
		return localContact;
	}

	public void setLocalContact(String localContact) {
		this.localContact = localContact;
	}

	public Integer getFaxAreaCd() {
		return faxAreaCd;
	}

	public void setFaxAreaCd(Integer faxAreaCd) {
		this.faxAreaCd = faxAreaCd;
	}

	public Integer getFaxExch() {
		return faxExch;
	}

	public void setFaxExch(Integer faxExch) {
		this.faxExch = faxExch;
	}

	public Integer getFaxExt() {
		return faxExt;
	}

	public void setFaxExt(Integer faxExt) {
		this.faxExt = faxExt;
	}

	public Integer getCustAreaCd() {
		return custAreaCd;
	}

	public void setCustAreaCd(Integer custAreaCd) {
		this.custAreaCd = custAreaCd;
	}

	public Integer getCustExch() {
		return custExch;
	}

	public void setCustExch(Integer custExch) {
		this.custExch = custExch;
	}

	public Integer getCustExt() {
		return custExt;
	}

	public void setCustExt(Integer custExt) {
		this.custExt = custExt;
	}

	public String getPhoneBase() {
		if (phoneBase != null) {
			return phoneBase.trim();
		}
		return phoneBase;
	}

	public void setPhoneBase(String phoneBase) {
		this.phoneBase = phoneBase;
	}

	public String getAddtlInfo() {
		if (addtlInfo != null) {
			return addtlInfo.trim();
		}
		return addtlInfo;
	}

	public void setAddtlInfo(String addtlInfo) {
		this.addtlInfo = addtlInfo;
	}

	public CustomerLocalInfo(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long customerId, Long terminalId) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.customerId = customerId;
		this.terminalId = terminalId;
	}

	public CustomerLocalInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerLocalInfo(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CustomerLocalInfo [customerId=" + customerId + ", terminalId=" + terminalId + ", customerName="
				+ customerName + ", custAddr1=" + custAddr1 + ", custAddr2=" + custAddr2 + ", customerCity="
				+ customerCity + ", custZipCd=" + custZipCd + ", customerState=" + customerState + ", custCountry="
				+ custCountry + ", localContact=" + localContact + ", faxAreaCd=" + faxAreaCd + ", faxExch=" + faxExch
				+ ", faxExt=" + faxExt + ", custAreaCd=" + custAreaCd + ", custExch=" + custExch + ", custExt="
				+ custExt + ", addtlInfo=" + addtlInfo + ", phoneBase=" + phoneBase + ", getUversion()=" + getUversion()
				+ ", getCreateUserId()=" + getCreateUserId() + ", getCreateDateTime()=" + getCreateDateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdateDateTime()=" + getUpdateDateTime()
				+ ", getUpdateExtensionSchema()=" + getUpdateExtensionSchema() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
