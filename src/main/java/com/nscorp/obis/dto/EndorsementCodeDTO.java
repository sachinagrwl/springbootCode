package com.nscorp.obis.dto;

import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

public class EndorsementCodeDTO extends AuditInfoDTO {

	@Size(max=CommonConstants.END_CD_TYPE_MAX_SIZE, message="Endorsement Code size should not be more than {max}")
    private String endorsementCd;
    
	@Size(max=CommonConstants.END_DESC_TYPE_MAX_SIZE, message="Endorsement Desc size should not be more than {max}")
    private String endorseCdDesc;
	public String getEndorsementCd() {
		return endorsementCd;
	}
	public void setEndorsementCd(String endorsementCd) {
		this.endorsementCd = endorsementCd;
	}
	public String getEndorseCdDesc() {
		return endorseCdDesc;
	}
	public void setEndorseCdDesc(String endorseCdDesc) {
		this.endorseCdDesc = endorseCdDesc;
	}

    
}
