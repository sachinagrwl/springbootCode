package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DamageCodeConversionDTO extends AuditInfoDTO{

	@NotNull(message = "Category Code Should not be Blank or Null.")
	private Integer catCd;
	
	@NotBlank(message = "Reason Code should not be Blank or Null")
	private String reasonCd;
	
	@Digits(integer =5, fraction = 0, message = "AARJobCode must be less than 5")
	private Integer aarJobCode;
	
	@Digits(integer =2, fraction = 0, message = "AARWhyMadeCode must be less than 2")
	private Integer aarWhyMadeCode;
}
