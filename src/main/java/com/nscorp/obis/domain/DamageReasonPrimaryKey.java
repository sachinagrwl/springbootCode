package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class DamageReasonPrimaryKey implements Serializable {

	@Column(name = "CAT_CD", columnDefinition = "SMALLINT(5)", nullable = false)
	private Integer catCd;

	@Column(name = "REASON_CD", columnDefinition = "CHAR(1)", nullable = false)
	private String reasonCd;

	public Integer getCatCd() {
		return catCd;
	}

	public void setCatCd(Integer catCd) {
		this.catCd = catCd;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	@Override
	public String toString() {
		return "DamageReasonPrimaryKey [catCd=" + catCd + ", reasonCd=" + reasonCd + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(catCd, reasonCd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageReasonPrimaryKey other = (DamageReasonPrimaryKey) obj;
		return Objects.equals(catCd, other.catCd) && Objects.equals(reasonCd, other.reasonCd);
	}

}
