package com.nscorp.obis.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EQ_OVERRIDE_TARE_WGT")
public class EquipmentOverrideTareWeight extends AuditInfo {
	
	@Id
    @Column(name = "OVERRIDE_ID", columnDefinition = "Double", nullable = false)
    private Long overrideId;

    @Column(name = "EQ_INIT", columnDefinition = "char(4)", nullable = false)
    private String equipmentInit;

    @Column(name = "EQ_NR_LOW", columnDefinition = "Decimal(19)", nullable = true)
    private BigDecimal equipmentNumberLow;

    @Column(name = "EQ_NR_HIGH", columnDefinition = "Decimal(19)", nullable = true)
    private BigDecimal equipmentNumberHigh;

    @Transient
    private EquipmentType equipmentTypeCode;
    
    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = true)
    private String equipmentType;

    @Column(name = "EQ_LGTH", columnDefinition = "smallint", nullable = true)
    private Integer equipmentLength;

    @Column(name = "OVERRIDE_TARE_WGT", columnDefinition = "integer", nullable = true)
    private Integer overrideTareWeight;

	public EquipmentOverrideTareWeight(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema, Long overrideId,
			String equipmentInit, BigDecimal equipmentNumberLow, BigDecimal equipmentNumberHigh,
			EquipmentType equipmentTypeCode, String equipmentType, Integer equipmentLength,
			Integer overrideTareWeight) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.overrideId = overrideId;
		this.equipmentInit = equipmentInit;
		this.equipmentNumberLow = equipmentNumberLow;
		this.equipmentNumberHigh = equipmentNumberHigh;
		this.equipmentTypeCode = equipmentTypeCode;
		this.equipmentType = equipmentType;
		this.equipmentLength = equipmentLength;
		this.overrideTareWeight = overrideTareWeight;
	}

	public Long getOverrideId() {
		return overrideId;
	}

	public void setOverrideId(Long overrideId) {
		this.overrideId = overrideId;
	}

	public String getEquipmentInit() {
		if(equipmentInit != null) {
			return equipmentInit.trim();
		}
		else {
			return equipmentInit;
		}
	}

	public void setEquipmentInit(String equipmentInit) {
		if(equipmentInit != null)
			this.equipmentInit = equipmentInit.toUpperCase();
		else 
			this.equipmentInit = equipmentInit;
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

	public EquipmentType getEquipmentTypeCode() {
		return equipmentTypeCode;
	}

	public void setEquipmentTypeCode(EquipmentType equipmentTypeCode) {
		this.equipmentTypeCode = equipmentTypeCode;
	}

	public String getEquipmentType() {
		if(equipmentType != null) {
			return equipmentType.trim();
		}
		else {
			return equipmentType;
		}
	}

	public void setEquipmentType(String equipmentType) {
		if(equipmentType != null){
			this.equipmentType = equipmentType.toUpperCase();
		}
		else {
			this.equipmentType = equipmentType;
		}
	}

	public Integer getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(Integer equipmentLength) {
		this.equipmentLength = equipmentLength;
	}

	public Integer getOverrideTareWeight() {
		return overrideTareWeight;
	}

	public void setOverrideTareWeight(Integer overrideTareWeight) {
		this.overrideTareWeight = overrideTareWeight;
	}

	public EquipmentOverrideTareWeight() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentOverrideTareWeight(String uversion, String createUserId, Timestamp createDateTime,
			String updateUserId, Timestamp updateDateTime, String updateExtensionSchema) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		// TODO Auto-generated constructor stub
	}

}
