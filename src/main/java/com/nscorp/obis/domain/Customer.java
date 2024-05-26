package com.nscorp.obis.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CUSTOMER")
public class Customer extends AuditInfo implements Serializable {
	@Id
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Column(name = "CORP_CUST_ID", columnDefinition = "Double(15)", nullable = true)
	private Long corporateCustomerId;

	@Column(name = "CUST_NM", columnDefinition = "char(35)", nullable = true)
	private String customerName;

	@Column(name = "CUST_NR", columnDefinition = "char(10)", nullable = true)
	private String customerNumber;

	@Column(name = "CUST_ADDR_1", columnDefinition = "char(35)", nullable = true)
	private String customerAdd1;

	@Column(name = "CUST_ADDR_2", columnDefinition = "char(35)", nullable = true)
	private String customerAdd2;

	@Column(name = "CUST_ST_PV", columnDefinition = "char(2)", nullable = true)
	private String customerState;

	@Column(name = "CUST_CTY", columnDefinition = "char(19)", nullable = true)
	private String customerCity;

	@Column(name = "CUST_ZIP_CD", columnDefinition = "char(10)", nullable = true)
	private String customerZipCode;

	@Column(name = "CUST_CNTRY", columnDefinition = "char(3)", nullable = true)
	private String customerCountry;

	@Column(name = "PRIME_CONTACT", columnDefinition = "char(30)", nullable = true)
	private String primeContact;

	@Column(name = "CUST_AREA_CD", columnDefinition = "Integer", nullable = true)
	private Integer customerArea;

	@Column(name = "CUST_EXCH", columnDefinition = "Smallint", nullable = true)
	private Integer customerExchange;

	@Column(name = "CUST_BASE", columnDefinition = "char(4)", nullable = true)
	private String customerBase;

	@Column(name = "CUST_EXT", columnDefinition = "smallint", nullable = true)
	private Integer customerExt;

	@Column(name = "TEAM_AUD_CD", columnDefinition = "char(3)", nullable = true)
	private String teamAudCd;

	public String getTeamAudCd() {
		return teamAudCd;
	}

	public void setTeamAudCd(String teamAudCd) {
		this.teamAudCd = teamAudCd;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CUST_ID", nullable = true)
	private DeliveryDetail deliveryDetail;

	public String getCustomerAdd1() {
		if (customerAdd1 != null) {
			return customerAdd1.trim();
		} else {
			return customerAdd1;
		}
	}

	public void setCustomerAdd1(String customerAdd1) {
		this.customerAdd1 = customerAdd1;
	}

	public String getCustomerAdd2() {
		if (customerAdd2 != null) {
			return customerAdd2.trim();
		} else {
			return customerAdd2;
		}
	}

	public void setCustomerAdd2(String customerAdd2) {
		this.customerAdd2 = customerAdd2;
	}

	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerCity() {
		if (customerCity != null) {
			return customerCity.trim();
		} else {
			return customerCity;
		}
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerZipCode() {
		return customerZipCode;
	}

	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getPrimeContact() {
		return primeContact;
	}

	public void setPrimeContact(String primeContact) {
		this.primeContact = primeContact;
	}

	public Integer getCustomerArea() {
		return customerArea;
	}

	public void setCustomerArea(Integer customerArea) {
		this.customerArea = customerArea;
	}

	public Integer getCustomerExchange() {
		return customerExchange;
	}

	public void setCustomerExchange(Integer customerExchange) {
		this.customerExchange = customerExchange;
	}

	public String getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(String customerBase) {
		this.customerBase = customerBase;
	}

	public Integer getCustomerExt() {
		return customerExt;
	}

	public void setCustomerExt(Integer customerExt) {
		this.customerExt = customerExt;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCorporateCustomerId() {
		return corporateCustomerId;
	}

	public void setCorporateCustomerId(Long corporateCustomerId) {
		this.corporateCustomerId = corporateCustomerId;
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

	public String getCustomerNumber() {
		if (customerNumber != null) {
			return customerNumber.trim();
		}
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public DeliveryDetail getDeliveryDetail() {
		return deliveryDetail;
	}

	public void setDeliveryDetail(DeliveryDetail deliveryDetail) {
		this.deliveryDetail = deliveryDetail;
	}

	public Customer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long customerId, Long corporateCustomerId,
			String customerName, String customerNumber, String customerAdd1, String customerAdd2, String customerState,
			String customerCity, String customerZipCode, String customerCountry, String primeContact,
			Integer customerArea, Integer customerExchange, String customerBase, Integer customerExt,
			DeliveryDetail deliveryDetail) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.customerId = customerId;
		this.corporateCustomerId = corporateCustomerId;
		this.customerName = customerName;
		this.customerNumber = customerNumber;
		this.customerAdd1 = customerAdd1;
		this.customerAdd2 = customerAdd2;
		this.customerState = customerState;
		this.customerCity = customerCity;
		this.customerZipCode = customerZipCode;
		this.customerCountry = customerCountry;
		this.primeContact = primeContact;
		this.customerArea = customerArea;
		this.customerExchange = customerExchange;
		this.customerBase = customerBase;
		this.customerExt = customerExt;
		this.deliveryDetail = deliveryDetail;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
}
