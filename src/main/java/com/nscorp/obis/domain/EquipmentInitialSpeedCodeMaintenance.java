package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="EQ_INIT_SPEED_CD")
@IdClass(EquipmentInitialSpeedCodeMaintenancePrimaryKeys.class)
public class EquipmentInitialSpeedCodeMaintenance extends AuditInfo {
	
	@Id
	@Column(name = "EQ_TP", columnDefinition = "Char(1)", nullable = false)
	private String eqType;
	
	@Id
	@Column(name = "EQ_INIT_SHORT", columnDefinition = "Char(4)", nullable = false)
	private String eqInitShort;
	
	@Column(name = "EQ_INIT", columnDefinition = "Char(4)", nullable = true)
	private String eqInit;

	public String getEqType() {
		if(eqType != null) {
			return eqType.trim();
		}
		else {
			return eqType;
		}
	}

	public void setEqType(String eqType) {
		this.eqType = eqType;
	}

	public String getEqInitShort() {
		if(eqInitShort != null) {
			return eqInitShort.trim();
		}
		else {
			return eqInitShort;
		}
	}

	public void setEqInitShort(String eqInitShort) {
		this.eqInitShort = eqInitShort;
	}

	public String getEqInit() {
		if(eqInit != null) {
			return eqInit.trim();
		}
		else {
			return eqInit;
		}
	}

	public void setEqInit(String eqInit) {
		this.eqInit = eqInit;
	}

	public EquipmentInitialSpeedCodeMaintenance(String eqType, String eqInitShort, String eqInit) {
		super();
		this.eqType = eqType;
		this.eqInitShort = eqInitShort;
		this.eqInit = eqInit;
	}

	public EquipmentInitialSpeedCodeMaintenance() {
		super();
		// TODO Auto-generated constructor stub
	}

}
