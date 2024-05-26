package com.nscorp.obis.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StationRestrictionPrimaryKeys implements Serializable {

	private long stationCrossReferenceId;
	private String carType;
	private String freightType;
	
	public long getStationCrossReferenceId() {
		return stationCrossReferenceId;
	}

	public void setStationCrossReferenceId(long stationCrossReferenceId) {
		this.stationCrossReferenceId = stationCrossReferenceId;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getFreightType() {
		return freightType;
	}

	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}

	public StationRestrictionPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StationRestrictionPrimaryKeys(long stationCrossReferenceId, String carType, String freightType) {
		super();
		this.stationCrossReferenceId = stationCrossReferenceId;
		this.carType = carType;
		this.freightType = freightType;
	}
	
}
