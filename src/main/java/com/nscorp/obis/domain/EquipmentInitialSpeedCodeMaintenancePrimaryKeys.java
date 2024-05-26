package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class EquipmentInitialSpeedCodeMaintenancePrimaryKeys implements Serializable {
	
	private String eqType;
	
	private String eqInitShort;

	public EquipmentInitialSpeedCodeMaintenancePrimaryKeys(String eqType, String eqInitShort) {
		super();
		this.eqType = eqType;
		this.eqInitShort = eqInitShort;
	}

	public EquipmentInitialSpeedCodeMaintenancePrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(eqInitShort, eqType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentInitialSpeedCodeMaintenancePrimaryKeys other = (EquipmentInitialSpeedCodeMaintenancePrimaryKeys) obj;
		return Objects.equals(eqInitShort, other.eqInitShort) && Objects.equals(eqType, other.eqType);
	}

}
