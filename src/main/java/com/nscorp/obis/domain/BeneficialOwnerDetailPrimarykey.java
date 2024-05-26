package com.nscorp.obis.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class BeneficialOwnerDetailPrimarykey implements Serializable {

	@Column(name = "BNF_CUST", columnDefinition = "Double(15)", nullable = false)
	private Long bnfCustId;

	@Column(name = "BNF_OWNR_NR", columnDefinition = "char(10)", nullable = false)
	private String bnfOwnerNumber;

	public Long getBnfCustId() {
		return bnfCustId;
	}

	public void setBnfCustId(Long bnfCustId) {
		this.bnfCustId = bnfCustId;
	}

	public String getBnfOwnerNumber() {
		return bnfOwnerNumber;
	}

	public void setBnfOwnerNumber(String bnfOwnerNumber) {
		this.bnfOwnerNumber = bnfOwnerNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bnfCustId, bnfOwnerNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeneficialOwnerDetailPrimarykey other = (BeneficialOwnerDetailPrimarykey) obj;
		return Objects.equals(bnfCustId, other.bnfCustId) && Objects.equals(bnfOwnerNumber, other.bnfOwnerNumber);
	}

}
