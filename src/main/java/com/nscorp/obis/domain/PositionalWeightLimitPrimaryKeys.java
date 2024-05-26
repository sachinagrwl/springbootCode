package com.nscorp.obis.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PositionalWeightLimitPrimaryKeys implements Serializable {

	private String carInit;
	private Long carNrLow;
	private Long carNrHigh;
	private String carEquipmentType;
	
	public String getCarInit() {
		return carInit;
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
		return carEquipmentType;
	}

	public void setCarEquipmentType(String carEquipmentType) {
		this.carEquipmentType = carEquipmentType;
	}

	public PositionalWeightLimitPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PositionalWeightLimitPrimaryKeys(String carInit, Long carNrLow, Long carNrHigh,
			String carEquipmentType) {
		super();
		this.carInit = carInit;
		this.carNrLow = carNrLow;
		this.carNrHigh = carNrHigh;
		this.carEquipmentType = carEquipmentType;
	}
	
}
