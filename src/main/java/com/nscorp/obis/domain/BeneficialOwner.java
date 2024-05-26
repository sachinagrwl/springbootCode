package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BENEOWNER")
public class BeneficialOwner extends AuditInfo {
    
    @Id
	@Column(name = "BNF_CUST", columnDefinition = "Double(15)", nullable = false)
	private Long bnfCustomerId;

	@Column(name = "BNF_LONG_NM", columnDefinition = "char(30)", nullable = true)
	private String bnfLongName;

	@Column(name = "BNF_SHORT_NM", columnDefinition = "char(10)", nullable = true)
	private String bnfShortName;

	@Column(name = "CUST_ID", columnDefinition = "Double(15)", nullable = false)
	private Long customerId;

	@Column(name = "CATEGORY", columnDefinition = "char(30)", nullable = true)
	private String category;

	@Column(name = "SUB_CATEGORY", columnDefinition = "char(30)", nullable = true)
	private String subCategory;

	@Column(name = "ACCOUNT_MANAGER", columnDefinition = "char(30)", nullable = true)
	private String accountManager;

	public Long getBnfCustomerId() {
		return bnfCustomerId;
	}

	public void setBnfCustomerId(Long bnfCustomerId) {
		this.bnfCustomerId = bnfCustomerId;
	}

	public String getBnfLongName() {
		if(bnfLongName != null) {
			return bnfLongName.trim().toUpperCase();
		}
		else {
			return bnfLongName;
		}
	}

	public void setBnfLongName(String bnfLongName) {
		this.bnfLongName = bnfLongName;
	}

	public String getBnfShortName() {
		return bnfShortName!=null?bnfShortName.trim().toUpperCase():bnfShortName;
	}

	public void setBnfShortName(String bnfShortName) {
		this.bnfShortName = bnfShortName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCategory() {
		if(category != null) {
			return category.trim();
		}
		else {
			return category;
		}
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory!=null?subCategory.trim().toUpperCase():subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getAccountManager() {
		return accountManager!=null?accountManager.trim().toUpperCase():accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public BeneficialOwner(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long bnfCustomerId, String bnfLongName,
			String bnfShortName, Long customerId, String category, String subCategory, String accountManager) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.bnfCustomerId = bnfCustomerId;
		this.bnfLongName = bnfLongName;
		this.bnfShortName = bnfShortName;
		this.customerId = customerId;
		this.category = category;
		this.subCategory = subCategory;
		this.accountManager = accountManager;
	}

	public BeneficialOwner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeneficialOwner(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BeneficialOwner [bnfCustomerId=" + bnfCustomerId + ", bnfLongName=" + bnfLongName + ", bnfShortName="
				+ bnfShortName + ", customerId=" + customerId + ", category=" + category + ", subCategory="
				+ subCategory + ", accountManager=" + accountManager + "]";
	}
    
	
}
