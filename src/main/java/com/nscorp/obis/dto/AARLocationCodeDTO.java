package com.nscorp.obis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AARLocationCodeDTO extends AuditInfoDTO{

	@NotNull(message = "W-LOC_CD Required")
	@Size(max=3, message = "Max.size exceeded")
    private String locCd;
	
	@Size(max=20, message = "LocDesc should not be more than 20 character")
    private String locDesc;

}
