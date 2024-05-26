package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UnCdDTO extends AuditInfoDTO{

	@NotBlank(message = "'UnCd' should not be Blank or Null.")
	@Size(max=6, message = "UnCd should not be more than 6 character")
	private String unCd;
	
	@Size(max=30, message = "UnDsc should not be more than 30 character")
	private String unDsc;

	private Long unInstructCd;
}
