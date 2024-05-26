package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class ConventionalEquipmentWidthPrimaryKey implements Serializable {
	
 	@Column(name = "UMLER_ID", columnDefinition = "double", nullable = false)
    private Long umlerId;

    @Column(name = "AAR_1ST_NR", columnDefinition = "char(1)", nullable = false)
    private String aar1stNr;

	public ConventionalEquipmentWidthPrimaryKey(Long umlerId, String aar1stNr) {
		super();
		this.umlerId = umlerId;
		this.aar1stNr = aar1stNr;
	}

	public ConventionalEquipmentWidthPrimaryKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aar1stNr == null) ? 0 : aar1stNr.hashCode());
		result = prime * result + ((umlerId == null) ? 0 : umlerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConventionalEquipmentWidthPrimaryKey other = (ConventionalEquipmentWidthPrimaryKey) obj;
		if (aar1stNr == null) {
			if (other.aar1stNr != null)
				return false;
		} else if (!aar1stNr.equals(other.aar1stNr))
			return false;
		if (umlerId == null) {
			if (other.umlerId != null)
				return false;
		} else if (!umlerId.equals(other.umlerId))
			return false;
		return true;
	}

}
