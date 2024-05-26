package com.nscorp.obis.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(EquipmentChassisPrimaryKey.class)
@Table(name = "EQ_CHAS")
public class EquipmentChassis {
	
	@Column(name="DMGE_IND", columnDefinition = "char(1)", nullable = true)
    private String dmgInd;
    
	@Id
	@Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = false)
	private String chasInit;
   
	@Id
	@Column(name = "CHAS_NR",length = 19, columnDefinition = "decimal", nullable = false) 
	private BigDecimal chasNbr;

@Id
	@Column(name = "CHAS_EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String chasEqTp;


	@Column(name = "CHAS_TP", columnDefinition = "char(1)", nullable = false)
	private String chasTp;
	
	@Id
	@Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = false)
	private String chasId;

	public String getChasEqTp() {
		return chasEqTp;
	}

	public void setChasEqTp(String chasEqTp) {
		this.chasEqTp = chasEqTp;
	}

	public String getChasInit() {
		return chasInit;
	}
	public void setChasInit(String chasInit) {
		this.chasInit = chasInit;
	}
	public BigDecimal getChasNbr() {
		return chasNbr;
	}
	public void setChasNbr(BigDecimal chasNbr) {
		this.chasNbr = chasNbr;
	}
	public String getChasTp() {
		return chasTp;
	}
	public void setChasTp(String chasTp) {
		this.chasTp = chasTp;
	}
	public String getChasId() {
		if(chasId!=null) {
			return chasId.trim();
		}
		return chasId;
	}
	public void setChasId(String chasId) {
		this.chasId = chasId;
	}
	public String getDmgInd() {
		return dmgInd;
	}
	public void setDmgInd(String dmgInd) {
		this.dmgInd = dmgInd;
	}
	public EquipmentChassis(String dmgInd, String chasInit,String chasEqTp, BigDecimal chasNbr, String chasTp, String chasId) {
		super();
		this.dmgInd = dmgInd;
		this.chasInit = chasInit;
		this.chasNbr = chasNbr;
		this.chasTp = chasTp;
		this.chasId = chasId;
		this.chasEqTp=chasEqTp;
	}
	public EquipmentChassis() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    
}