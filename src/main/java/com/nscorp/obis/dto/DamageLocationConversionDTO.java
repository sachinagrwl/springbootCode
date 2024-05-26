package com.nscorp.obis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DamageLocationConversionDTO extends AuditInfoDTO {
	@Size(max=20, message = "Location Description can't be more than 20 character")
	@NotNull(message = "Loc Desc can't be null")
    private String locDscr;
	
	@Size(max=3, message = "Location Code can't be more than 3 character")
    private String locCd;
	
	@Size(max=20, message = "Description can't be more than 20 character")
    private String locDesc;
}
