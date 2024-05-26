package com.nscorp.obis.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AARHitchPrimaryKeys implements Serializable {
	
	private String aarType;
	
	private String hitchLocation;

	public String getAarType() {
		return aarType;
	}

	public void setAarType(String aarType) {
		this.aarType = aarType;
	}

	public String getHitchLocation() {
		return hitchLocation;
	}

	public void setHitchLocation(String hitchLocation) {
		this.hitchLocation = hitchLocation;
	}

	public AARHitchPrimaryKeys(String aarType, String hitchLocation) {
		super();
		this.aarType = aarType;
		this.hitchLocation = hitchLocation;
	}

	public AARHitchPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}

}
