package com.nscorp.obis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResourceListDTO extends AuditInfoDTO{
	
	@NotNull(message = "Name Should not be Null.")
	@Schema(required = true,description="The name of the resource being used.", example="APPLY_BLOK")
	private String resourceName;
	
	@Schema(required = false,description="The description of the resource being used.", example="APPLY DMG PRV BLOK INSP HOLD")
	private String resourceDescription;
		
}
