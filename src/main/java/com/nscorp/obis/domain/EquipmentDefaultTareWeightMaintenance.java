package com.nscorp.obis.domain;

import javax.persistence.*;

@Entity
@Table(name="EQ_TARE_WGT")
@IdClass(EquipmentTareWeightPrimaryKeys.class)
public class EquipmentDefaultTareWeightMaintenance extends AuditInfo {
	@Id
	@Column(name = "EQ_TP", columnDefinition = "Char(1)", nullable = false)
	private String eqTp;
	
	@Id
	@Column(name = "EQ_LGTH", columnDefinition = "Smallint", nullable = false)
	private Integer eqLgth;
	
	@Column(name = "TARE_WGT", columnDefinition = "Integer", nullable = true)
	private Integer tareWgt;

	public String getEqTp() {
		if(eqTp != null) {
			return eqTp.trim();
		}
		else {
			return null;
		}
	}

	public void setEqTp(String eqTp) {
		this.eqTp = eqTp;
	}

	public Integer getEqLgth() {
		return eqLgth;
	}

	public void setEqLgth(Integer eqLgth) {
		this.eqLgth = eqLgth;
	}

	public Integer getTareWgt() {
		return tareWgt;
	}

	public void setTareWgt(Integer tareWgt) {
		this.tareWgt = tareWgt;
	}

	public EquipmentDefaultTareWeightMaintenance(String eqTp, Integer eqLgth, Integer tareWgt) {
		super();
		this.eqTp = eqTp;
		this.eqLgth = eqLgth;
		this.tareWgt = tareWgt;
	}

	public EquipmentDefaultTareWeightMaintenance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
