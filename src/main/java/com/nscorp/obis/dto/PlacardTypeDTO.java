package com.nscorp.obis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlacardTypeDTO extends AuditInfoDTO{
	
	@NotNull(message = "PlacardCd can't be Null")
	private String placardCd;
	
	@Size(max=30, message="Placard Long Desc size should be max {max}")
	private String placardLongDesc;

	@Size(max=10, message="Placard Short Desc size should be max {max}")
	private String placardShortDesc;

}
