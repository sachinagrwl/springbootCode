package com.nscorp.obis.domain;

import java.io.Serializable;

import javax.persistence.Column;

public class DamageLocationCompositePrimaryKeys implements Serializable {
    
	@Column(name = "LOC_CD", columnDefinition = "SMALLINT", nullable = false)
	private Integer locCd;

	@Column(name = "CAT_CD", columnDefinition = "SMALLINT", nullable = false)
	private Integer catCd;

	public Integer getLocCd() {
		return locCd;
	}

	public void setLocCd(Integer locCd) {
		this.locCd = locCd;
	}

	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	@Override
	public String toString() {
		return "DamageLocationCompositePrimaryKeys [locCd=" + locCd + ", catCd=" + catCd + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catCd == null) ? 0 : catCd.hashCode());
		result = prime * result + ((locCd == null) ? 0 : locCd.hashCode());
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
		DamageLocationCompositePrimaryKeys other = (DamageLocationCompositePrimaryKeys) obj;
		if (catCd == null) {
			if (other.catCd != null)
				return false;
		} else if (!catCd.equals(other.catCd))
			return false;
		if (locCd == null) {
			if (other.locCd != null)
				return false;
		} else if (!locCd.equals(other.locCd))
			return false;
		return true;
	}

	
}
