package com.nscorp.obis.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name="CORP_CUST")
public class CorporateCustomer extends AuditInfo implements Serializable{

	@Id
	@Column(name = "CORP_CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long corporateCustomerId;
	
	@Column(name = "CORP_LONG_NM", columnDefinition = "Char(30)", nullable = false)
	private String corporateLongName;
	
	@Column(name = "CORP_SHORT_NM", columnDefinition = "Char(10)", nullable = false)
	private String corporateShortName;
	
	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;
	
	@Column(name = "ICHG_CD", columnDefinition = "Char(4)", nullable = true)
	private String icghCd;
	
	@Column(name = "PRIMARY_LOB", columnDefinition = "Char(1)", nullable = true)
	private String primaryLob;
	
	@Column(name = "SECONDARY_LOB", columnDefinition = "Char(10)", nullable = true)
	private String secondaryLob;
	
	@Column(name = "SCAC", columnDefinition = "Char(4)", nullable = true)
	private String scac;
	
	@Column(name = "TERMINAL_FEED_ENABLED", columnDefinition = "Char(1)", nullable = true)
	private String terminalFeedEnabled;
	
	@Column(name = "ACCOUNT_MANAGER", columnDefinition = "Char(30)", nullable = true)
	private String accountManager;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CORP_CUST_ID", referencedColumnName = "CORP_CUST_ID", insertable = false , updatable = false)
	private StorageOverrideBillToParty storageOverrideBillToParty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	private Customer customer;
	
	@Transient
	private List<CorporateCustomerDetail> corporateCustomerDetail;
	

	public StorageOverrideBillToParty getStorageOverrideBillToParty() {
		return storageOverrideBillToParty;
	}

	public void setStorageOverrideBillToParty(StorageOverrideBillToParty storageOverrideBillToParty) {
		this.storageOverrideBillToParty = storageOverrideBillToParty;
	}

	public List<CorporateCustomerDetail> getCorporateCustomerDetail() {
		return corporateCustomerDetail;
	}

	public void setCorporateCustomerDetail(List<CorporateCustomerDetail> corporateCustomerDetail) {
		this.corporateCustomerDetail = corporateCustomerDetail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCorporateCustomerId() {
		return corporateCustomerId;
	}

	public void setCorporateCustomerId(Long corporateCustomerId) {
		this.corporateCustomerId = corporateCustomerId;
	}

	public String getCorporateLongName() {
		if(corporateLongName != null) {
			return corporateLongName.trim();
		}
		else {
			return corporateLongName;
		}
	}

	public void setCorporateLongName(String corporateLongName) {
		this.corporateLongName = corporateLongName;
	}

	public String getCorporateShortName() {
		if(corporateShortName != null) {
			return corporateShortName.trim();
		}
		else {
			return corporateShortName;
		}
	}

	public void setCorporateShortName(String corporateShortName) {
		this.corporateShortName = corporateShortName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getIcghCd() {
		if(icghCd != null) {
			return icghCd.trim();
		}
		else {
			return icghCd;
		}
	}

	public void setIcghCd(String icghCd) {
		this.icghCd = icghCd;
	}

	public String getPrimaryLob() {
		if(primaryLob != null) {
			return primaryLob.trim();
		}
		else {
			return primaryLob;
		}
	}

	public void setPrimaryLob(String primaryLob) {
		this.primaryLob = primaryLob;
	}

	public String getSecondaryLob() {
		if(secondaryLob != null) {
			return secondaryLob.trim();
		}
		else {
			return secondaryLob;
		}
	}

	public void setSecondaryLob(String secondaryLob) {
		this.secondaryLob = secondaryLob;
	}

	public String getScac() {
		if(scac != null) {
			return scac.trim();
		}
		else {
			return scac;
		}
	}

	public void setScac(String scac) {
		this.scac = scac;
	}

	public String getTerminalFeedEnabled() {
		if(terminalFeedEnabled != null) {
			return terminalFeedEnabled.trim();
		}
		else {
			return terminalFeedEnabled;
		}
	}

	public void setTerminalFeedEnabled(String terminalFeedEnabled) {
		this.terminalFeedEnabled = terminalFeedEnabled;
	}

	public String getAccountManager() {
		if(accountManager != null) {
			return accountManager.trim();
		}
		else {
			return accountManager;
		}
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}



	public CorporateCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CorporateCustomer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long corporateCustomerId, String corporateLongName,
			String corporateShortName, Long customerId, String icghCd, String primaryLob, String secondaryLob, String scac,
			String terminalFeedEnabled, String accountManager, Customer customer,
			List<CorporateCustomerDetail> corporateCustomerDetail, StorageOverrideBillToParty storageOverrideBillToParty) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.corporateCustomerId = corporateCustomerId;
		this.corporateLongName = corporateLongName;
		this.corporateShortName = corporateShortName;
		this.customerId = customerId;
		this.icghCd = icghCd;
		this.primaryLob = primaryLob;
		this.secondaryLob = secondaryLob;
		this.scac = scac;
		this.terminalFeedEnabled = terminalFeedEnabled;
		this.accountManager = accountManager;
		this.customer = customer;
		this.corporateCustomerDetail = corporateCustomerDetail;
		this.storageOverrideBillToParty = storageOverrideBillToParty;
	}

/**
 * @param uversion
 * @param createUserId
 * @param createDateTime
 * @param updateUserId
 * @param updateDateTime
 * @param updateExtensionSchema
 */
public CorporateCustomer(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
		Timestamp updateDateTime, String updateExtensionSchema) {
	super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
	// TODO Auto-generated constructor stub
}
	

}
