package com.nscorp.obis.domain;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "HAZ_RESTRICTION")
public class HazRestriction extends AuditInfo {

    @Id
    @Column(name = "UN_CD", columnDefinition = "char(6)", nullable = false)
    private String unCd;

    @Column(name = "RESTR_CLS", columnDefinition = "char(1)", nullable = false)
    private String restoreClass;


	public String getUnCd() {
		if(unCd != null) {
			return unCd.trim();
		}
		else {
			return unCd;
		}
	}

	public void setUnCd(String unCd) {
		this.unCd = unCd;
	}

	public String getRestoreClass() {
		return restoreClass;
	}

	public void setRestoreClass(String restoreClass) {
		this.restoreClass = restoreClass;
	}

	public HazRestriction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String unCd, String restoreClass) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.unCd = unCd;
		this.restoreClass = restoreClass;
	}

	public HazRestriction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HazRestriction(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
	
}

