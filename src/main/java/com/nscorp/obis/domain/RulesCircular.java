package com.nscorp.obis.domain;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "RULES_CIRCULAR")
@IdClass(RulesCircularPrimaryKeys.class)
public class RulesCircular extends AuditInfo {

    @Transient
    private EquipmentType equipmentTypeCode;
    
    @Id
    @Column(name = "EQ_TP", columnDefinition = "char(1)", nullable = false)
    private String equipmentType;

	@Id
    @Column(name = "EQ_LGTH", columnDefinition = "smallint", nullable = false)
    private Integer equipmentLength;

    @Column(name = "MAX_SHIP_WGT", columnDefinition = "integer", nullable = true)
    private Integer maximumShipWeight;

    public RulesCircular() {
        super();
    }

	public EquipmentType getEquipmentTypeCode() {
		if("C".equalsIgnoreCase(this.equipmentType))
			this.equipmentTypeCode = EquipmentType.CONTAINER;
		if("T".equalsIgnoreCase(this.equipmentType))
			this.equipmentTypeCode = EquipmentType.TRAILER;
		if("Z".equalsIgnoreCase(this.equipmentType))
			this.equipmentTypeCode = EquipmentType.CHASSIS;
		if("F".equalsIgnoreCase(this.equipmentType))
			this.equipmentTypeCode = EquipmentType.FLATCAR;
		if("G".equalsIgnoreCase(this.equipmentType))
			this.equipmentTypeCode = EquipmentType.GENSET;
		return equipmentTypeCode;

	}

	public void setEquipmentTypeCode(EquipmentType equipmentTypeCode) {
		if(equipmentTypeCode.equals(EquipmentType.CONTAINER))
			this.equipmentType = "C";
		if(equipmentTypeCode.equals(EquipmentType.TRAILER))
			this.equipmentType = "T";
		if(equipmentTypeCode.equals(EquipmentType.CHASSIS))
			this.equipmentType = "Z";
		if(equipmentTypeCode.equals(EquipmentType.FLATCAR))
			this.equipmentType = "F";
		if(equipmentTypeCode.equals(EquipmentType.GENSET))
			this.equipmentType = "G";
	}

	public String getEquipmentType() {
		return equipmentType;
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

	public Integer getMaximumShipWeight() {
		return maximumShipWeight;
	}

	public void setMaximumShipWeight(Integer maximumShipWeight) {
		this.maximumShipWeight = maximumShipWeight;
	}

	public RulesCircular(String uversion, String createUserId, Timestamp createDateTime, String updateUserId,
			Timestamp updateDateTime, String updateExtensionSchema, EquipmentType equipmentTypeCode,
			String equipmentType, Integer equipmentLength, Integer maximumShipWeight) {
		super(uversion, createUserId, createDateTime, updateUserId, updateDateTime, updateExtensionSchema);
		this.equipmentTypeCode = equipmentTypeCode;
		this.equipmentType = equipmentType;
		this.equipmentLength = equipmentLength;
		this.maximumShipWeight = maximumShipWeight;
	}  
   
}
