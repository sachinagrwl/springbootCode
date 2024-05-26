package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StackEquipmentWidthDTO extends AuditInfoDTO {

	@Schema(required = true,description="The Umler Id of the Railcar being used.", example="1.4163614650827E13")
	private Long umlerId;

	@Schema(required = false,description="This gives the minimum equipment length, in feet.", example="40")
	@Digits(integer=CommonConstants.MIN_EQ_LGTH_MAX_SIZE, fraction=0,message="'minimumEquipmentLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'minimumEquipmentLength' value must be greater than or equal to 0")
	private Integer minimumEquipmentLength;

	@Schema(required = false,description="This gives the maximum equipment length, in feet.", example="40")
	@Digits(integer=CommonConstants.MAX_EQ_LGTH_MAX_SIZE, fraction=0,message="'maximumEquipmentLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'maximumEquipmentLength' value must be greater than or equal to 0")
	private Integer maximumEquipmentLength;

	@Schema(required = false,description="This gives the minimum equipment width, in feet.", example="40")
	@Digits(integer=CommonConstants.MIN_EQ_WIDTH_MAX_SIZE, fraction=0,message="'minimumEquipmentWidth' cannot have more than 5 digits")
	@Min(value = 0, message = "'minimumEquipmentWidth' value must be greater than or equal to 0")
	private Integer minimumEquipmentWidth;

	@Schema(required = false,description="This gives the maximum equipment width, in feet.", example="40")
	@Digits(integer=CommonConstants.MAX_EQ_WIDTH_MAX_SIZE, fraction=0,message="'maximumEquipmentWidth' cannot have more than 5 digits")
	@Min(value = 0, message = "'maximumEquipmentWidth' value must be greater than or equal to 0")
	private Integer maximumEquipmentWidth;

}
