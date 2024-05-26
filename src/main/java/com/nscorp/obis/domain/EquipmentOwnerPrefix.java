package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EQ_OWNR_PREFIX")
public class EquipmentOwnerPrefix extends AuditInfo {
	
	
	@Id
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipInit;
	
	@Column(name = "OWNRSHIP", columnDefinition = "char(1)", nullable = false)
    private String ownership;
	
	@Column(name = "ICHG_CD", columnDefinition = "char(4)", nullable = false)
    private String interchangeCd;
	
	
	
	public EquipmentOwnerPrefix(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, String equipInit, String ownership,
			String interchangeCd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.equipInit = equipInit;
		this.ownership = ownership;
		this.interchangeCd = interchangeCd;
	}
	
	

	public EquipmentOwnerPrefix() {
		super();
		// TODO Auto-generated constructor stub
	}



	public EquipmentOwnerPrefix(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}



	public String getEquipInit() {
		return equipInit;
	}

	public void setEquipInit(String equipInit) {
		this.equipInit = equipInit;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getInterchangeCd() {
		if (interchangeCd != null) {
			return interchangeCd.trim();
		} else {
			return interchangeCd;
		}
	}

	public void setInterchangeCd(String interchangeCd) {
		this.interchangeCd = interchangeCd;
	}
    
}