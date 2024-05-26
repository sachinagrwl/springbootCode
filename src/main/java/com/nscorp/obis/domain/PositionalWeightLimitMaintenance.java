package com.nscorp.obis.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "POS_LOAD_LIMIT")
@IdClass(PositionalWeightLimitPrimaryKeys.class)
public class PositionalWeightLimitMaintenance extends AuditInfo {
	
	@Id
	@Column(name = "CAR_INIT", columnDefinition = "char(4)", nullable = false)
	private String carInit;
	
	@Id
	@Column(name = "CAR_NR_LOW", columnDefinition = "Decimal", nullable = false)
	private Long carNrLow;
	
	@Id
	@Column(name = "CAR_NR_HIGH", columnDefinition = "Decimal", nullable = false)
	private Long carNrHigh;
	
	@Id
	@Column(name = "CAR_EQ_TP", columnDefinition = "char(1)", nullable = false)
	private String carEquipmentType;
	
	@Column(name = "AAR_TP", columnDefinition = "char(4)", nullable = true)
	private String aarType;
	
	@Column(name = "CAR_OWNER", columnDefinition = "char(4)", nullable = true)
	private String carOwner;
	
	@Column(name = "C20_MAX_WGT", columnDefinition = "Integer", nullable = true)

	private Integer c20MaxWeight; // change it to wrapper class


	@Column(name = "CAR_DESC", columnDefinition = "char(58)", nullable = true)
	private String carDescription;


	public PositionalWeightLimitMaintenance(String uVersion, Timestamp createDateTime, String createUserId,
			Timestamp updateDateTime, String updateUserId, String updateExtentionSchema,
			@NotNull(message = "CarInit can not be null") String carInit, Long carNrLow, Long carNrHigh,
			String carEquipmentType, String aarType, String carOwner, Integer c20MaxWeight, String carDescription) {
		super();
		this.carInit = carInit;
		this.carNrLow = carNrLow;
		this.carNrHigh = carNrHigh;
		this.carEquipmentType = carEquipmentType;
		this.aarType = aarType;
		this.carOwner = carOwner;
		this.c20MaxWeight = c20MaxWeight;
		this.carDescription = carDescription;
	}
	
	public String getCarInit() {
		if(carInit != null) {
			return carInit.trim();
		}
		else {
			return carInit;
		}
	}

	public void setCarInit(String carInit) {
		this.carInit = carInit;
	}

	public Long getCarNrLow() {
		return carNrLow;
	}

	public void setCarNrLow(Long carNrLow) {
		this.carNrLow = carNrLow;
	}

	public Long getCarNrHigh() {
		return carNrHigh;
	}

	public void setCarNrHigh(Long carNrHigh) {
		this.carNrHigh = carNrHigh;
	}

	public String getCarEquipmentType() {
		if(carEquipmentType != null) {
			return carEquipmentType.trim();
		}
		else {
			return carEquipmentType;
		}
	}

	public void setCarEquipmentType(String carEquipmentType) {
		this.carEquipmentType = carEquipmentType;
	}

	public String getAarType() {
		if(aarType != null) {
			return aarType.trim();
		}
		else {
			return aarType;
		}
	}

	public void setAarType(String aarType) {
		this.aarType = aarType;
	}

	public String getCarOwner() {
		if(carOwner != null) {
			return carOwner.trim();
		}
		else {
			return carOwner;
		}
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Integer getC20MaxWeight() {
		return c20MaxWeight;
	}

	public void setC20MaxWeight(Integer c20MaxWeight) {
		this.c20MaxWeight = c20MaxWeight;
	}

	public String getCarDescription() {
		if(carDescription != null) {
			return carDescription.trim();
		}
		else {
			return carDescription;
		}
	}

	public void setCarDescription(String carDescription) {
		this.carDescription = carDescription;
	}

	public PositionalWeightLimitMaintenance() {
		super();
	// TODO Auto-generated constructor stub
	}

	

}
