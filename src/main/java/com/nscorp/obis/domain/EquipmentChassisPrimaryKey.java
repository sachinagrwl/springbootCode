package com.nscorp.obis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class EquipmentChassisPrimaryKey implements Serializable{

	
	@Column(name = "CHAS_INIT", columnDefinition = "char(4)", nullable = false)
	private String chasInit;
   
	@Column(name = "CHAS_NR",length = 19, columnDefinition = "decimal", nullable = false) 
	private BigDecimal chasNbr;
	
	@Column(name = "CHAS_EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String chasEqTp;
	
	@Column(name = "CHAS_ID", columnDefinition = "char(4)", nullable = false)
	private String chasId;

	public EquipmentChassisPrimaryKey(String chasInit, BigDecimal chasNbr, String chasEqTp, String chasId) {
		super();
		this.chasInit = chasInit;
		this.chasNbr = chasNbr;
		this.chasEqTp = chasEqTp;
		this.chasId = chasId;
	}

	public EquipmentChassisPrimaryKey() {
		super();
	}
}
