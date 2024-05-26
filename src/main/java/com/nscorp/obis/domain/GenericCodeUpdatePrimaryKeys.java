package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GenericCodeUpdatePrimaryKeys implements Serializable {

	private String genericTable;
	private String genericTableCode;
	public GenericCodeUpdatePrimaryKeys() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GenericCodeUpdatePrimaryKeys(String genericTable, String genericTableCode) {
		super();
		this.genericTable = genericTable.toUpperCase();
		this.genericTableCode = genericTableCode.toUpperCase();
	}

	@Override
	public int hashCode() {
		return Objects.hash(genericTable, genericTableCode);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericCodeUpdatePrimaryKeys other = (GenericCodeUpdatePrimaryKeys) obj;
		return Objects.equals(genericTable, other.genericTable) && Objects.equals(genericTableCode, other.genericTableCode);
	}
	
}
