package com.nscorp.obis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "EQ_RESTRICT")
public class EquipmentRestrict extends AuditInfo {

    @Id
    @Column(name = "RESTRICTION_ID", columnDefinition = "double", nullable = false)
    private Long restrictionId;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Transient
    private EquipmentTypeRestrict equipmentTypeCode;
    
    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;
    

    @Column(name = "EQ_NR_LOW", columnDefinition = "decimal", nullable = true)
    private BigDecimal equipmentNumberLow;

    @Column(name = "EQ_NR_HIGH", columnDefinition = "decimal", nullable = true)
    private BigDecimal equipmentNumberHigh;

    @Column(name = "RESTRICTION_TP", columnDefinition = "char(1)", nullable = true)
    private String equipmentRestrictionType;

	public Long getRestrictionId() {
		return restrictionId;
	}

	public void setRestrictionId(Long restrictionId) {
		this.restrictionId = restrictionId;
	}

	public String getEquipmentInit() {
		if (equipmentInit != null) {
			return equipmentInit.trim();
		} else{
			return equipmentInit;
		}
	}

	public void setEquipmentInit(String equipmentInit) {
		if(equipmentInit != null){
			this.equipmentInit = equipmentInit.toUpperCase();
		} else{
			this.equipmentInit = equipmentInit;
		}
	}

	public EquipmentTypeRestrict getEquipmentTypeCode() {
		return equipmentTypeCode;
	}

	public void setEquipmentTypeCode(EquipmentTypeRestrict equipmentTypeCode) {
		this.equipmentTypeCode = equipmentTypeCode;
	}

	public String getEquipmentType() {
		if(equipmentType != null){
			return equipmentType.trim();
		} else{
			return equipmentType;
		}

	}

	public void setEquipmentType(String equipmentType) {
		if(equipmentType != null){
			this.equipmentType = equipmentType.toUpperCase();
		} else{
			this.equipmentType = equipmentType;
		}
	}

	public BigDecimal getEquipmentNumberLow() {
		return equipmentNumberLow;
	}

	public void setEquipmentNumberLow(BigDecimal equipmentNumberLow) {
		this.equipmentNumberLow = equipmentNumberLow;
	}

	public BigDecimal getEquipmentNumberHigh() {
		return equipmentNumberHigh;
	}

	public void setEquipmentNumberHigh(BigDecimal equipmentNumberHigh) {
		this.equipmentNumberHigh = equipmentNumberHigh;
	}

	public String getEquipmentRestrictionType() {
		if(equipmentRestrictionType != null){
			return equipmentRestrictionType.trim();
		} else{
			return equipmentRestrictionType;
		}

	}

	public void setEquipmentRestrictionType(String equipmentRestrictionType) {
		if(equipmentRestrictionType != null){
			this.equipmentRestrictionType = equipmentRestrictionType.toUpperCase();
		} else{
			this.equipmentRestrictionType = equipmentRestrictionType;
		}
	}

	public EquipmentRestrict(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, Long restrictionId, String equipmentInit,
			EquipmentTypeRestrict equipmentTypeCode, String equipmentType, BigDecimal equipmentNumberLow,
			BigDecimal equipmentNumberHigh, String equipmentRestrictionType) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.restrictionId = restrictionId;
		this.equipmentInit = equipmentInit;
		this.equipmentTypeCode = equipmentTypeCode;
		this.equipmentType = equipmentType;
		this.equipmentNumberLow = equipmentNumberLow;
		this.equipmentNumberHigh = equipmentNumberHigh;
		this.equipmentRestrictionType = equipmentRestrictionType;
	}

	public EquipmentRestrict() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentRestrict(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}