package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.nscorp.obis.common.NullOrNotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DamageLocationDTO extends AuditInfoDTO{

	@Digits(integer=3, fraction=0, message="Location Code size should not be more than 3 digits")
	@NotNull(message = "W-LOC_CD Required")
	@Range(min = 1, max = 127)
    private Integer locCd;

	@Digits(integer=3, fraction=0, message="Category Code size should not be more than 3 digits")
	@NotNull(message = "W-CAT_CD Required")
	@Range(min = 1, max = 127)
	private Integer catCd;

	@Size(min=1, max=20, message = "Location Description length should be between 1 and 20.")
	@NotBlank(message = "W-LOC_DSCR Required")
	private String locationDscr;

	@Digits(integer=2, fraction=0, message="PrtOrder Max size exceeded")
	@Min(value = 1, message = "PrtOrder value must be greater than 0")
	private Integer prtOrder;
    
}
