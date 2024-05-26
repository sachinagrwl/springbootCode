package com.nscorp.obis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DamageCategoryDTO extends AuditInfoDTO{

	@NotNull(message = "W-CAT_CD Required")
	@Range(min=0, max=127,message="Code should not be more than 127") 
    private Integer catCd;

	@NotBlank(message = "W-CAT_DSCR Required")
	@Size(min=1, max=20, message = "Description should not be more than 20 Character")
    private String catDscr;

    private Integer prtOrder;
}
