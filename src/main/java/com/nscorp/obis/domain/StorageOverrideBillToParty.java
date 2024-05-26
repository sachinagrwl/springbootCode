package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STO_BTP_OVERRIDE")
public class StorageOverrideBillToParty extends AuditInfo {

	@Id
	@Column(name = "CORP_CUST_ID",length = 15, columnDefinition = "double", nullable = false)
    private Long corporateCustomerId;
	
	@Column(name = "OVERRIDE_IND", columnDefinition = "char(1)", nullable = false)
	private String overrideInd;

	public Long getCorporateCustomerId() {
		return corporateCustomerId;
	}

	public void setCorporateCustomerId(Long corporateCustomerId) {
		this.corporateCustomerId = corporateCustomerId;
	}
	
	public String getOverrideInd() {
		return overrideInd;
	}

	public void setOverrideInd(String overrideInd) {
		this.overrideInd = overrideInd;
	}

	public StorageOverrideBillToParty(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long corporateCustomerId,
			String overrideInd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.corporateCustomerId = corporateCustomerId;
		this.overrideInd = overrideInd;
	}

	public StorageOverrideBillToParty() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StorageOverrideBillToParty(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
}
