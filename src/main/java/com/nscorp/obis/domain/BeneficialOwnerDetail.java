package com.nscorp.obis.domain;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="BENEOWNER_DTL")
@IdClass(BeneficialOwnerDetailPrimarykey.class)
public class BeneficialOwnerDetail extends AuditInfo {

	@Id
	@Column(name = "BNF_CUST", columnDefinition = "Double(15)", nullable = false)
	private Long bnfCustId;

	@Id
	@Column(name = "BNF_OWNR_NR", columnDefinition = "char(10)", nullable = false)
	private String bnfOwnerNumber;

	@Transient
	private List<String> customer;

	public List<String> getCustomer() {
		return customer;
	}

	public void setCustomer(List<String> customer) {
		this.customer = customer;
	}

	public Long getBnfCustId() {
		return bnfCustId;
	}

	public void setBnfCustId(Long bnfCustId) {
		this.bnfCustId = bnfCustId;
	}

	public String getBnfOwnerNumber() {
		return bnfOwnerNumber!=null?bnfOwnerNumber.trim():bnfOwnerNumber;
	}

	public void setBnfOwnerNumber(String bnfOwnerNumber) {
		this.bnfOwnerNumber = bnfOwnerNumber;
	}

	@Override
	public String toString() {
		return "BeneficialOwnerDetail [bnfCustId=" + bnfCustId + ", bnfOwnerNumber=" + bnfOwnerNumber
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}

}
