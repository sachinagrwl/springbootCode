package com.nscorp.obis.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentInitialSpeedCodeMaintenanceDTO extends AuditInfoDTO {
	
	@NotBlank(message = "'eqType' should not be Blank or Null.")
	@Schema(required = true,description="The type of the equipment being used.", example="C")
	@Size(min=CommonConstants.EQ_TYPE_MIN_SIZE, max=CommonConstants.EQ_TYPE_MAX_SIZE, message ="'eqType' must be between {min} and {max}")
	private String eqType;
	
	@NotBlank(message = "'eqInitShort' should not be Blank or Null.")
	@Schema(required = true,description="The Short Init of the equipment being used.", example="CHBU")
	@Size(min=CommonConstants.EQ_INIT_SHORT_MIN_SIZE, max=CommonConstants.EQ_INIT_SHORT_MAX_SIZE, message ="'eqInitShort' must be between {min} and {max}")
	private String eqInitShort;
	
	@Schema(required = false,description="The Init of the equipment being used.", example="CHBU")
	@Size(min=CommonConstants.EQ_INIT_MIN_SIZE, max=CommonConstants.EQ_INIT_MAX_SIZE, message ="'eqInit' must be between {min} and {max}")
	private String eqInit;

}
