package com.nscorp.obis.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;

public class BeneficialOwnerDetailDTO extends AuditInfoDTO {

	@Schema(required = true, description = "beneficial owner customer id")
	private Long bnfCustId;

	@NotBlank(message = "Beneficial Owner Primary Six should not be null or empty")
	@Size(min = 0, max = 10, message = "beneficial owner number length should not be greater than 10")
	@Schema(required = true, description = "beneficial owner customer primary six")
	private String bnfOwnerNumber;

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
		return "BeneficialOwnerDetailDTO [bnfCustId=" + bnfCustId + ", bnfOwnerNumber=" + bnfOwnerNumber
				+ ", getUversion()=" + getUversion() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateDateTime()=" + getCreateDateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdateDateTime()=" + getUpdateDateTime() + ", getUpdateExtensionSchema()="
				+ getUpdateExtensionSchema() + "]";
	}

}
