package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EQ_RACK_RANGE")
public class EquipmentRackRange extends AuditInfo{
	
	@Id
	@Column(name = "EQ_RACK_RANGE_ID ",length = 15, columnDefinition = "double", nullable = false)
	private Long equipRackRangeId;
    
	@Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
	private String equipInit;
    
	@Column(name = "EQ_LOW_NR", length = 6, columnDefinition = "decimal", nullable = false)
	private BigDecimal equipLowNbr;
    
	@Column(name = "EQ_HIGH_NR", length = 6, columnDefinition = "decimal", nullable = false)
	private BigDecimal equipHighNbr;
    
	@Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String equipType;
    
	@Column(name = "AAR_TP",  columnDefinition = "char", nullable = true)
	private String aarType;
    
	@Column(name = "EQ_IND", columnDefinition = "char", nullable = true)
	private String equipInd;
	
	
	public Long getEquipRackRangeId() {
		return equipRackRangeId;
	}
	public void setEquipRackRangeId(Long equipRackRangeId) {
		this.equipRackRangeId = equipRackRangeId;
	}
	
	public String getEquipInit() {
		if(equipInit != null) {
			return equipInit.trim();
		}
		else {
			return equipInit;
		}
	}

	public void setEquipInit(String equipInit) {
		if(equipInit != null)
			this.equipInit = equipInit.toUpperCase();
		else 
			this.equipInit = equipInit;
	}

	public BigDecimal getEquipLowNbr() {
		return equipLowNbr;
	}
	public void setEquipLowNbr(BigDecimal equipLowNbr) {
		this.equipLowNbr = equipLowNbr;
	}
	public BigDecimal getEquipHighNbr() {
		return equipHighNbr;
	}
	public void setEquipHighNbr(BigDecimal equipHighNbr) {
		this.equipHighNbr = equipHighNbr;
	}
	public String getEquipType() {
		return equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	public String getAarType() {
		return aarType;
	}
	public void setAarType(String aarType) {
		this.aarType = aarType;
	}
	public String getEquipInd() {
		return equipInd;
	}
	public void setEquipInd(String equipInd) {
		this.equipInd = equipInd;
	}
	public EquipmentRackRange(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long equipRackRangeId, String equipInit,
			BigDecimal equipLowNbr, BigDecimal equipHighNbr, String equipType, String aarType, String equipInd) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.equipRackRangeId = equipRackRangeId;
		this.equipInit = equipInit;
		this.equipLowNbr = equipLowNbr;
		this.equipHighNbr = equipHighNbr;
		this.equipType = equipType;
		this.aarType = aarType;
		this.equipInd = equipInd;
	}
	public EquipmentRackRange() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EquipmentRackRange(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}
    
    

}