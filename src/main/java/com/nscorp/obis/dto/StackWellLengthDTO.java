package com.nscorp.obis.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.nscorp.obis.common.CommonConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StackWellLengthDTO extends AuditInfoDTO {

	@Schema(required = true,description="The Umler Id of the Railcar being used.", example="1.4163614650827E13")
	private Long umlerId;

	@Schema(required = true,description="The AAR 1st Number of the Stack Car being used.", example="P")
	@Size(min= CommonConstants.AAR_1ST_NO_MIN_SIZE, max=CommonConstants.AAR_1ST_NO_MAX_SIZE, message = "'aar1stNr' length should be between {min} and {max}")
	private String aar1stNr;

	@Schema(required = false,description="This gives the length, in feet, of the last well.", example="40")
	@Digits(integer=CommonConstants.END_WELL_LGTH_MAX_SIZE, fraction=0,message="'endWellLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'endWellLength' value must be greater than or equal to 0")
	private Integer endWellLength;

	@Schema(required = false,description="This gives the length, in feet, of the middle well.", example="40")
	@Digits(integer=CommonConstants.MED_WELL_LGTH_MAX_SIZE, fraction=0,message="'medWellLength' cannot have more than 5 digits")
	@Min(value = 0, message = "'medWellLength' value must be greater than or equal to 0")
	private Integer medWellLength;

}
