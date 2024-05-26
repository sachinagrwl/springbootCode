package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class EquipmentTareWeightPrimaryKeys implements Serializable {

	private String eqTp;
	private Integer eqLgth;
	
	
	public EquipmentTareWeightPrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EquipmentTareWeightPrimaryKeys(String eqTp, Integer eqLgth) {
		super();
		this.eqTp = eqTp;
		this.eqLgth = eqLgth;
	}


	@Override
	public int hashCode() {
		return Objects.hash(eqLgth, eqTp);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentTareWeightPrimaryKeys other = (EquipmentTareWeightPrimaryKeys) obj;
		return Objects.equals(eqLgth, other.eqLgth) && Objects.equals(eqTp, other.eqTp);
	}
	
	
}
